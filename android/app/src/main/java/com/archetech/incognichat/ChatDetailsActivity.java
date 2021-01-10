package com.archetech.incognichat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.media.AsyncPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.realm.Realm;

public class ChatDetailsActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName()+" ---> ";
    Realm db;
    Conversation conversation;
    ApplicationController applicationController;

    EditText EtNickname;
    TextView TvToken,TVConvoId,TvConvoPass,TvPublicKey,TvPrivateKey,BtnBlock;
    ImageView btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);

        Realm.init(this);
        db=Realm.getDefaultInstance();
        Realm.init(this);
        db=Realm.getDefaultInstance();

        TvToken=findViewById(R.id.txt_token);
        TVConvoId=findViewById(R.id.txt_conversation_id);
        TvConvoPass=findViewById(R.id.txt_conversation_password);
        TvPublicKey=findViewById(R.id.txt_public_key);
        TvPrivateKey=findViewById(R.id.txt_private_key);
        BtnBlock=findViewById(R.id.txt_block);
        btnSave=findViewById(R.id.btn_save);
        EtNickname = findViewById(R.id.et_nickname);

        applicationController = new ApplicationController(this);
        conversation = db.where(Conversation.class).equalTo("conversation_id",Long.parseLong(getIntent().getExtras().getString("conversation_id"))).findFirst();


        BtnBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockConversation();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        },10);

    }

    private void blockConversation()
    {
        final Request request = new JsonObjectRequest(Request.Method.POST, API.URL_CONVERSATION_BLOCK,new JSONObject(new HashMap<String, String>()
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
                    new ConversationController(getApplicationContext()).blockConversation(String.valueOf(conversation.getConversation_id()));
                    Toasty.warning(getApplicationContext(), "Conversation has been blocked", Toast.LENGTH_SHORT, true).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },1500);
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


    private void loadData()
    {
        TvToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = applicationController.getAccessToken();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Access Token", token);
                clipboard.setPrimaryClip(clip);
                Toasty.success(getApplicationContext(), "Access Token copied to clipborad", Toast.LENGTH_SHORT, true).show();
            }
        });

        TvPrivateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String privateKey = applicationController.getPrivateKey();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Private Key", privateKey);
                clipboard.setPrimaryClip(clip);
                Toasty.success(getApplicationContext(), "Private Key copied to clipborad", Toast.LENGTH_SHORT, true).show();
            }
        });

        TvPublicKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String publicKey = applicationController.getPublicKey();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Public Key", publicKey);
                    clipboard.setPrimaryClip(clip);
                    Toasty.success(getApplicationContext(), "Public Key copied to clipborad", Toast.LENGTH_SHORT, true).show();
            }
        });

        TvConvoPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String password = conversation.getPassword();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Conversation Password", password);
                    clipboard.setPrimaryClip(clip);
                    Toasty.success(getApplicationContext(), "Conversation Password copied to clipborad", Toast.LENGTH_SHORT, true).show();
            }
        });

        TVConvoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = String.valueOf(conversation.getConversation_id());
                ClipboardManager clipboard = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Conversation Id", id);
                clipboard.setPrimaryClip(clip);
                Toasty.success(getApplicationContext(), "Conversation Id copied to clipborad", Toast.LENGTH_SHORT, true).show();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNick = EtNickname.getText().toString().trim();
                if(newNick.length()>5)
                {
                    new ConversationController(getApplicationContext()).updateConversationNickName(String.valueOf(conversation.getConversation_id()),newNick);
                    Toasty.success(getApplicationContext(), "Nickname updated", Toast.LENGTH_SHORT, true).show();
                }
                else
                {
                    Toasty.error(getApplicationContext(), "Invalid length", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        EtNickname.setText(new ConversationController(getApplication()).getConversationNickname(String.valueOf(conversation.getConversation_id())));
    }


}
