package com.archetech.incognichat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONObject;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SearchProfileActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName() +" ---> ";

    SpinKitView spinKitView;
    TextView TvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profile);

        spinKitView=findViewById(R.id.spin_kit);
        TvResult=findViewById(R.id.result);


        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        if(appLinkData.getPathSegments().get(0).equals("i"))
        {
            String handle = String.valueOf(appLinkData.getPathSegments().get(1));
            if(new ConversationController(getApplication()).conversationAlreadyExistAgainstHandle(handle)==false)
            {
                getPublicKey(handle);
            }
            else
            {
                TvResult.setText("Conversation already exist\nOpening Conversation");
                spinKitView.setVisibility(View.INVISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startChatActivity(new ConversationController(getApplication()).getConversationIdAgainstHAndle(handle));
                    }
                },1000);
                Log.d(TAG,"created");

            }
        }
    }


    private void startChatActivity(String _conversationId)
    {
        Intent nextActivityIntent = new Intent(getApplicationContext(), ChatActivity.class);
        nextActivityIntent.putExtra("conversation_id",_conversationId);
        nextActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(nextActivityIntent);
    }

    private void startHomeActivity()
    {
        Intent nextActivityIntent = new Intent(getApplicationContext(), HomeActivity.class);
        nextActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(nextActivityIntent);
    }


    private void getPublicKey(String _handle)
    {
        final Request request = new JsonObjectRequest(Request.Method.POST, API.URL_GET_PUBLIC_KEY,new JSONObject(new HashMap<String, String>()
        {
            {
                put("handle", _handle);
            }
        }), new Response.Listener<JSONObject>()
        {
            @Override public void onResponse(JSONObject response)
            {
                String status = Helper.getValueFromJSONObject(response,"status");
                Log.d(TAG," CODE : "+status);
                if(status.equals("200"))
                {
                    String public_key = Helper.getValueFromJSONObject(response,"public_key");
                    try {
                        TvResult.setText("User found.\nPerforming Security Handshake");
                        spinKitView.setVisibility(View.INVISIBLE);
                        createConversation(public_key,_handle);
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    }
                }
                if(status.equals("403"))
                {
                    TvResult.setText("Can not chat with yourself");
                    spinKitView.setVisibility(View.INVISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startHomeActivity();
                        }
                    },1000);
                }
                if(status.equals("404"))
                {
                    TvResult.setText("No User found against this handle");
                    spinKitView.setVisibility(View.INVISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startHomeActivity();
                        }
                    },1000);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
                TvResult.setText("Unable to chat this person.");
                spinKitView.setVisibility(View.INVISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startHomeActivity();
                    }
                },1000);
            }
        })
        {
            @Override public Map<String, String> getHeaders() throws AuthFailureError
            {
                return new ApplicationController(getApplicationContext()).getAuthenticationHeader();
            }
        };
        RequestQueueSingleton.getInstance(getApplication()).addToRequestQueue(request);
    }


    private void createConversation(String _recipientPublicKey,String _handle) throws GeneralSecurityException {
        Crypto crypto = new Crypto();
        final String publicKeyForConversation = crypto.public_key;
        final String conversationPassword = UUID.randomUUID().toString();
        final String encryptedConversationPassword = Crypto.EncryptRSA(conversationPassword,_recipientPublicKey);
        final String salt = Crypto.GetSalt();

        final Request request = new JsonObjectRequest(Request.Method.POST, API.URL_SEARCH,new JSONObject(new HashMap<String, String>()
        {
            {
                put("handle", _handle);
                put("public_key", publicKeyForConversation);
                put("encrypted_password", encryptedConversationPassword);
                put("salt",salt);
            }
        }), new Response.Listener<JSONObject>()
        {
            @Override public void onResponse(JSONObject response)
            {
                String status = Helper.getValueFromJSONObject(response,"status");
                if(status.equals("200"))
                {
                    String conversation_id = Helper.getValueFromJSONObject(response,"conversation_id");
                    registerConversation(conversation_id,conversationPassword,salt,_handle);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG,"Error ---> ");
                error.printStackTrace();
                TvResult.setText("Unable to chat this person.");
                spinKitView.setVisibility(View.INVISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startHomeActivity();
                    }
                },1000);
            }
        })
        {
            @Override public Map<String, String> getHeaders() throws AuthFailureError
            {
                return new ApplicationController(getApplicationContext()).getAuthenticationHeader();
            }
        };
        RequestQueueSingleton.getInstance(getApplication()).addToRequestQueue(request);
    }

    private void registerConversation(String _conversationId,String _password,String _salt,String _handle)
    {
        ConversationController conversationController=new ConversationController(this);
        if(conversationController.createNewConversation(_conversationId,_password,_salt,false,_handle))
        {
            TvResult.setText("User found.\nOpening Conversation");
            spinKitView.setVisibility(View.INVISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startChatActivity(_conversationId);
                }
            },1000);
            Log.d(TAG,"created");
        }
        else
        {
            Log.d(TAG,"error");
            TvResult.setText("Unable to chat this person.");
            spinKitView.setVisibility(View.INVISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startHomeActivity();
                }
            },1000);
        }
    }
}
