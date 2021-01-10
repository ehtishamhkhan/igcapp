package com.archetech.incognichat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.ikovac.timepickerwithseconds.TimePicker;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ChatActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName()+" ---> ";

    Realm db;
    BroadcastReceiver newMessageBroadcastReceiver,newReceivedAcknowledgementBroadcastReceiver,newConversationSeenBroadcastReceiver;

    Conversation conversation;
    RealmResults<Message> messagesList;
    ListView MessagesListView;
    MessagesAdapter messagesAdapter;
    ImageButton ButtonSend;
    EditText EditTextComposer;
    String composedMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Realm.init(this);

        if(Helper.isAppOnForeground(getApplicationContext()))
        {

        }


        db=Realm.getDefaultInstance();
        MessagesListView = findViewById(R.id.messages_list_view);
        ButtonSend = findViewById(R.id.btn_send);
        EditTextComposer=findViewById(R.id.et_composer);

        conversation = db.where(Conversation.class).equalTo("conversation_id",Long.parseLong(getIntent().getExtras().getString("conversation_id"))).findFirst();

        if(conversation.getBlocked())
        {
            EditTextComposer.setText("This conversation is blocked");
            EditTextComposer.setEnabled(false);
            ButtonSend.setEnabled(false);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadMessages();
            }
        },10);

        ButtonSend.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                scheduleMessage();
                return false;
            }
        });

        ButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditTextComposer.getText().toString().trim().length()>0)
                {
                    composedMessage = EditTextComposer.getText().toString();
                    EditTextComposer.setText("");
                    ComposeMessage();
                }
                else
                {
                    Log.d(TAG,"empty message");
                }
            }
        });

        initializeBroadcastReceiver();
        sendConversationSeenAcknowledgement();

    }

    private void scheduleMessage()
    {
        String message = EditTextComposer.getText().toString().trim();
        Calendar cal = Calendar.getInstance();
        if(message.length()>0)
        {
            MyTimePickerDialog mTimePicker = new MyTimePickerDialog(this, new MyTimePickerDialog.OnTimeSetListener() {

                Calendar rightNow = Calendar.getInstance();
                int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                int minute = rightNow.get(Calendar.MINUTE);
                int second = rightNow.get(Calendar.SECOND);

                @Override
                public void onTimeSet(TimePicker view, int hourSet, int minuteSet, int secondsSet) {
//                    // TODO Auto-generated method stub
//				String time = (" Message Scheduled at" + String.format("%02d", hourOfDay)+
//						":" + String.format("%02d", minute) +
//						":" + String.format("%02d", seconds));
//                    Toasty.success(getApplicationContext(), time, Toast.LENGTH_SHORT, true).show();
                    hour = hourSet-hour;

                    minute = minuteSet -minute;

                    second = secondsSet-second;

                    second = second + (minute*60)+(hour*3600);

                    if(second>=5)
                    {
                        Toasty.success(getApplicationContext(), String.valueOf(second), Toast.LENGTH_SHORT, true).show();
                        final Request request = new JsonObjectRequest(Request.Method.POST, API.URL_SEND_SELF,new JSONObject(new HashMap<String, String>()
                        {
                            {
                                put("conversation_id",String.valueOf(conversation.getConversation_id()));
                                put("text",EditTextComposer.getText().toString());
                                put("seconds",String.valueOf(second));
                            }
                        }), new Response.Listener<JSONObject>()
                        {
                            @Override public void onResponse(JSONObject response)
                            {
                                String status = Helper.getValueFromJSONObject(response,"status");
                                if(status.equals("200"))
                                {
                                    Log.d(TAG,"ok");
                                }
                                else
                                {
                                    Log.d(TAG,"not ok");
                                }
                            }
                        }, new Response.ErrorListener()
                        {
                            @Override public void onErrorResponse(VolleyError error)
                            {
                                Log.d(TAG,error.toString());
                            }
                        }){
                            @Override public Map<String, String> getHeaders() throws AuthFailureError
                            {
                                return new ApplicationController(getApplicationContext()).getAuthenticationHeader();
                            }
                        };
                        RequestQueueSingleton.getInstance(getApplication()).addToRequestQueue(request);
                    }
                    else
                    {
                        Toasty.error(getApplicationContext(), "Invalid schedule, atleast 5 seconds from now!", Toast.LENGTH_SHORT, true).show();
                    }

                }
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), true);
            mTimePicker.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(newMessageBroadcastReceiver);
        unregisterReceiver(newConversationSeenBroadcastReceiver);
        unregisterReceiver(newReceivedAcknowledgementBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(messagesAdapter!=null)
            messagesAdapter.notifyDataSetChanged();
    }

    private void initializeBroadcastReceiver()
    {
        IntentFilter filterMessageReceived = new IntentFilter();
        filterMessageReceived.addAction("com.archetech.incognichat.newMessage");
        newMessageBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if(Helper.isAppOnForeground(getApplicationContext()))
                {
                    sendConversationSeenAcknowledgement();
                    messagesAdapter.notifyDataSetChanged();
                    if(conversation!=null)
                    if(conversation.getBlocked())
                    {
                        EditTextComposer.setText("This conversation is blocked");
                        EditTextComposer.setEnabled(false);
                        ButtonSend.setEnabled(false);
                    }
                }
                Log.d(TAG,"New Message Broadcast Received");
            }
        };

        IntentFilter filterMessageReceivedAcknowledgement = new IntentFilter();
        filterMessageReceivedAcknowledgement.addAction("com.archetech.incognichat.newReceivedAcknowledgement");
        newReceivedAcknowledgementBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                messagesAdapter.notifyDataSetChanged();
                Log.d(TAG,"New Message Received Acknowledgement Received");
            }
        };

        IntentFilter filterConversationSeenAcknowledgement = new IntentFilter();
        filterConversationSeenAcknowledgement.addAction("com.archetech.incognichat.newConversationSeenAcknowledgement");
        newConversationSeenBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                messagesAdapter.notifyDataSetChanged();
                Log.d(TAG,"New Conversation Seen Acknowledgement Received");
            }
        };
        registerReceiver(newMessageBroadcastReceiver, filterMessageReceived);
        registerReceiver(newConversationSeenBroadcastReceiver,filterConversationSeenAcknowledgement);
        registerReceiver(newReceivedAcknowledgementBroadcastReceiver,filterMessageReceivedAcknowledgement);
    }

    private void sendConversationSeenAcknowledgement()
    {
        final Request request = new JsonObjectRequest(Request.Method.POST, API.URL_CONVERSATION_SEEN_ACKNOWLEDGEMENT,new JSONObject(new HashMap<String, String>()
        {
            {
                put("conversation_id",String.valueOf(conversation.getConversation_id()));
            }
        }), new Response.Listener<JSONObject>()
        {
            @Override public void onResponse(JSONObject response)
            {
                String status = Helper.getValueFromJSONObject(response,"status");
                if(status.equals("200"))
                {
                    Log.d(TAG,"ok");
                    if(new MessageController(getApplicationContext()).setReceivedSeen(String.valueOf(conversation.getConversation_id())))
                    {
                        Log.d(TAG,"Received Messages Seen");
                    }
                }
                else
                {
                    Log.d(TAG,"not ok");
                }
            }
        }, new Response.ErrorListener()
        {
            @Override public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG,error.toString());
            }
        }){
            @Override public Map<String, String> getHeaders() throws AuthFailureError
            {
                return new ApplicationController(getApplicationContext()).getAuthenticationHeader();
            }
        };
        RequestQueueSingleton.getInstance(getApplication()).addToRequestQueue(request);
    }

    private void ComposeMessage()
    {
        final String conversationId = String.valueOf(conversation.getConversation_id());
        final String replyId = "0";

        final Request request = new JsonObjectRequest(Request.Method.POST, API.URL_COMPONSE_MESSAGE,new JSONObject(new HashMap<String, String>()
        {
            {
                put("conversation_id", conversationId);
                put("reply_id", replyId);
            }
        }), new Response.Listener<JSONObject>()
        {
            @Override public void onResponse(JSONObject response)
            {
                String status = Helper.getValueFromJSONObject(response,"status");
                if(status.equals("200"))
                {
                    String messageId = Helper.getValueFromJSONObject(response,"message_id");
                    try {
                        SendMessage(messageId,conversation.getConversation_id());
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener()
        {
            @Override public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG,error.toString());
            }
        }){
            @Override public Map<String, String> getHeaders() throws AuthFailureError
            {
                return new ApplicationController(getApplicationContext()).getAuthenticationHeader();
            }
        };
        RequestQueueSingleton.getInstance(getApplication()).addToRequestQueue(request);
    }

    private void SendMessage(final String _messageId,final Long _conversationId) throws GeneralSecurityException, UnsupportedEncodingException {
        final String encrypted = Crypto.EncryptAES(composedMessage,conversation.getSalt(),conversation.getPassword());
        EditTextComposer.setText("");
        final String decrypted = Crypto.DecryptAES(encrypted,conversation.getSalt(),conversation.getPassword());
        final String replyId="0";


        if(new MessageController(getApplicationContext()).createNewSentMessage(_messageId,_conversationId,decrypted,"0"));
        {
            messagesAdapter.notifyDataSetChanged();
        }


        final Request request = new JsonObjectRequest(Request.Method.POST, API.URL_SEND_MESSAGE,new JSONObject(new HashMap<String, String>()
        {
            {
                put("message_id", _messageId);
                put("conversation_id", String.valueOf(_conversationId));
                put("message", encrypted);
                put("reply_id", replyId);
            }
        }), new Response.Listener<JSONObject>()
        {
            @Override public void onResponse(JSONObject response)
            {
                String status = Helper.getValueFromJSONObject(response,"status");
                if(status.equals("200"))
                {
                    if(new MessageController(getApplicationContext()).setMessageSent(_messageId))
                    {
                        Log.d(TAG,"Message Sent");
                        messagesAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener()
        {
            @Override public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG,error.toString());
            }
        }){
            @Override public Map<String, String> getHeaders() throws AuthFailureError
            {
                return new ApplicationController(getApplicationContext()).getAuthenticationHeader();
            }
        };
        RequestQueueSingleton.getInstance(getApplication()).addToRequestQueue(request);
    }


    private void LoadMessages()
    {
        messagesList = db.where(Message.class).equalTo("conversation_id",conversation.getConversation_id()).findAll().sort("message_id", Sort.ASCENDING);
        messagesAdapter=new MessagesAdapter(this,messagesList);
        MessagesListView.setAdapter(messagesAdapter);
    }
}
