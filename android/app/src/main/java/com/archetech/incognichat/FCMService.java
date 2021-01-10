package com.archetech.incognichat;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FCMService extends FirebaseMessagingService {

    public final String TAG = this.getClass().getSimpleName() +" ---> ";

    Uri sound;

    Intent newMessageBroadcast,newReceivedAcknowledgementBroadcast,newConversationSeenAcknowledgement;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeBroadcast();
        sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    private void initializeBroadcast()
    {
        newMessageBroadcast = new Intent();
        newMessageBroadcast.setAction("com.archetech.incognichat.newMessage");

        newReceivedAcknowledgementBroadcast = new Intent();
        newReceivedAcknowledgementBroadcast.setAction("com.archetech.incognichat.newReceivedAcknowledgement");

        newConversationSeenAcknowledgement = new Intent();
        newConversationSeenAcknowledgement.setAction("com.archetech.incognichat.newConversationSeenAcknowledgement");
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG,"New Push Message Received");
        String status = Helper.getValueFromRemoteMessage(remoteMessage,"status");

        if(status.equals("200"))
        {
            try {
                performAction(remoteMessage);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void performAction(RemoteMessage _message) throws GeneralSecurityException, UnsupportedEncodingException {
        String code = Helper.getValueFromRemoteMessage(_message,"code");
        switch (code)
        {
            case "1":
            {
                Log.d(TAG,"Incoming Message");
                handleIncomingMessage(_message);
            }break;
            case "2":
            {
                Log.d(TAG,"New Message received");
                receiveNewMessageReceivedAcknowledgement(_message);
            }break;
            case "3":
            {
                Log.d(TAG,"New Conversation Seen");
                handleConversationSeenAcknowledgement(_message);
            }break;
            case "4":
            {
                Log.d(TAG,"New Conversation blocked");
                handleConversationBlock(_message);
            }break;
            case "5":
            {
                Log.d(TAG,"New Self Message");
                handleNewSelfMessage(_message);
            }break;
        }
    }

    private  void handleNewSelfMessage(RemoteMessage _message)
    {
        ConversationController conversationController = new ConversationController(getApplicationContext());
        String conversationId = Helper.getValueFromRemoteMessage(_message,"conversation_id");
        if(conversationController.conversationExist(conversationId))
        {
            Log.d(TAG,"Conversation found");
            String messageId = Helper.getValueFromRemoteMessage(_message,"message_id");
            String message = Helper.getValueFromRemoteMessage(_message,"text");
            String replyId = Helper.getValueFromRemoteMessage(_message,"reply_id");

            MessageController messageController = new MessageController(getApplicationContext());
            if(messageController.createNewReceivedMessage(messageId,conversationId,message,replyId))
            {
                sendBroadcast(newMessageBroadcast);
                sendReceivedAcknowledgement(messageId);
                notifyNewMessage(conversationId);
                conversationController.updateConversationPriority(conversationId);
                Log.d(TAG,"Added Message");
            }
            else
            {
                Log.d(TAG,"Error adding msg");
            }
        }
    }


    private void handleConversationBlock(RemoteMessage _message)
    {
        ConversationController conversationController = new ConversationController(getApplicationContext());
        String conversationId = Helper.getValueFromRemoteMessage(_message,"conversation_id");
        if(conversationController.conversationExist(conversationId))
        {
            Log.d(TAG,"Conversation found\n Blocking Conversation");
            conversationController.blockConversation(conversationId);
            sendBroadcast(newMessageBroadcast);
        }
        else
        {
            Log.d(TAG,"Conversation doesn't exist for blocking");
        }
    }

    private void sendReceivedAcknowledgement(String _messageId)
    {
        final Request request = new JsonObjectRequest(Request.Method.POST, API.URL_SEND_MESSAGE_RECEIVERD_ACKNOWLEDGEMENT,new JSONObject(new HashMap<String, String>()
        {
            {
                put("message_id",_messageId);
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

    private void handleIncomingMessage(RemoteMessage _message) throws GeneralSecurityException, UnsupportedEncodingException {
        Log.d(TAG,"Adding new Incoming Message");
        ConversationController conversationController = new ConversationController(getApplicationContext());
        String conversationId = Helper.getValueFromRemoteMessage(_message,"conversation_id");
        if(conversationController.conversationExist(conversationId))
        {
            Log.d(TAG,"Conversation found");
            String messageId = Helper.getValueFromRemoteMessage(_message,"message_id");
            String encryptedMessage = Helper.getValueFromRemoteMessage(_message,"text");
            String replyId = Helper.getValueFromRemoteMessage(_message,"reply_id");
            String decryptedMessage = Crypto.DecryptAES(encryptedMessage,conversationController.getConversationSalt(conversationId),conversationController.getConversationPassword(conversationId));
            Log.d(TAG,decryptedMessage);
            MessageController messageController = new MessageController(getApplicationContext());
            if(messageController.createNewReceivedMessage(messageId,conversationId,decryptedMessage,replyId))
            {
                sendBroadcast(newMessageBroadcast);
                sendReceivedAcknowledgement(messageId);
                notifyNewMessage(conversationId);
                conversationController.updateConversationPriority(conversationId);
                Log.d(TAG,"Added Message");
            }
            else
            {
                Log.d(TAG,"Error adding msg");
            }
        }
        else
        {
            Log.d(TAG,"Conversation doesn't exist, creating new conversation");
            receiveNewConversation(_message,conversationId);
        }
    }

    private void handleConversationSeenAcknowledgement(RemoteMessage _message)
    {
        String conversationId = Helper.getValueFromRemoteMessage(_message,"conversation_id");
        if(new MessageController(getApplicationContext()).setSeen(conversationId))
        {
            sendBroadcast(newConversationSeenAcknowledgement);
            Log.d(TAG,"SEEN");
        }
        else
        {
            Log.d(TAG,"NOT SEEN");
        }
    }

    private void receiveNewMessageReceivedAcknowledgement(RemoteMessage _message)
    {
        String messageId = Helper.getValueFromRemoteMessage(_message,"message_id");
        if(new MessageController(getApplicationContext()).setMessageReceived(messageId))
        {
            sendBroadcast(newReceivedAcknowledgementBroadcast);
            Log.d(TAG,"RECEIVED");
        }
        else
        {
            Log.d(TAG,"NOT RECEIVED");
        }

    }

    private void receiveNewConversation(final RemoteMessage _message, String _conversationId)
    {
        final Request request = new JsonObjectRequest(Request.Method.POST, API.URL_GET_CONVERSATION_CREDENTIALS,new JSONObject(new HashMap<String, String>()
        {
            {
                put("conversation_id",_conversationId);
            }
        }), new Response.Listener<JSONObject>()
        {
            @Override public void onResponse(JSONObject response)
            {
                String status = Helper.getValueFromJSONObject(response,"status");
                if(status.equals("200"))
                {
                    String password = Helper.getValueFromJSONObject(response,"encrypted_password");
                    Log.d(TAG,"password: "+password);
                    password = Crypto.DecryptRSA(password,new ApplicationController(getApplicationContext()).getPrivateKey());
                    Log.d(TAG,"decrypted: "+password);
                    String salt = Helper.getValueFromJSONObject(response,"salt");
                    Log.d(TAG,"salt: "+salt);
                    if(new ConversationController(getApplicationContext()).createNewConversation(_conversationId,password,salt,true,""))
                    {
                        try {
                            Log.d(TAG,"Conversation Created");
                            handleIncomingMessage(_message);
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Log.d(TAG,"Error: Conversation Controller did not create new conversation");
                    }
                }
                else
                {
                    Log.d(TAG,"Conversation is not created: error");
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

    public void notifyNewMessage(String _conversationId)
    {
        String nickname = new ConversationController(getApplicationContext()).getConversationNickname(_conversationId);
        if(Helper.isAppOnForeground(getApplicationContext())==false)
        {
            Intent intent=new Intent(this, ChatActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("conversation_id",_conversationId);
            generateNotification("New Message","from "+nickname,intent);
        }
        else
        {
            final MediaPlayer mp = MediaPlayer.create(this, sound);
            mp.start();
        }
    }

    private void generateNotification(String _title,String _body,Intent _intent)
    {
        Log.d(TAG,"Notification Generation is Called");
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"IncogniChat")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New IncogniChat Message")
            .setContentText(_body)
            .setContentIntent(pendingIntent)
            .setDefaults(android.app.Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setSound(sound)
            .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());

    }


}
