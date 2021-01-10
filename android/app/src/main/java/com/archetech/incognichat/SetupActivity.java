package com.archetech.incognichat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;

import io.realm.Realm;

public class SetupActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName() +" ---> ";

    Button BtnInitialize;
    SpinKitView SetupSpinKit;
    TextView TvSetupWait;

    ApplicationController applicationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        BtnInitialize=findViewById(R.id.btn_setup);
        TvSetupWait=findViewById(R.id.txt_wait);
        SetupSpinKit=findViewById(R.id.spin_kit_wait);

        Realm.init(this);

        applicationController=new ApplicationController(this);

        BtnInitialize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnInitialize.setEnabled(false);
                TvSetupWait.setVisibility(View.VISIBLE);
                SetupSpinKit.setVisibility(View.VISIBLE);
                createOrRetrieveFCMToken();
            }
        });
    }

    private void createOrRetrieveFCMToken()
    {
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    String msg = token.toString();
                    Log.d(TAG, msg);
//                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    GoIncognito(token.toString());
                }
            });
    }

    private void GoIncognito(String _fcm)
    {
        Crypto crypto=new Crypto();
        String publicKey=crypto.public_key;
        String privateKey=crypto.private_Key;
        String deviceId= Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        final Request request = new JsonObjectRequest(Request.Method.POST, API.URL_REGISTER,new JSONObject(new HashMap<String, String>()
            {
                {
                put("device_id", deviceId);
                put("public_key", publicKey);
                put("fcm_token", _fcm);
                }
            }), new Response.Listener<JSONObject>()
                {
                    @Override public void onResponse(JSONObject response)
                    {
                        String status = Helper.getValueFromJSONObject(response,"status");
                        if(status.equals("200"))
                        {
                            String access_token = Helper.getValueFromJSONObject(response,"access_token");
                            String handle = Helper.getValueFromJSONObject(response,"handle");
                            String public_key = Helper.getValueFromJSONObject(response,"public_key");
                            String nickname = Helper.getValueFromJSONObject(response,"nickname");
                            String reputation = Helper.getValueFromJSONObject(response,"reputation");
                            String views = Helper.getValueFromJSONObject(response,"views");
                            String badges_created = Helper.getValueFromJSONObject(response,"badges_created");
                            String version = Helper.getValueFromJSONObject(response,"version");
                            String recovery_pin = Helper.getValueFromJSONObject(response,"recovery_pin");
                            if(new ApplicationController(getApplicationContext()).InitializeApplication(nickname,access_token,version,handle,public_key,privateKey,recovery_pin,reputation,views,badges_created))
                            {
                                TvSetupWait.setText("Initialized");
                                BtnInitialize.setText("Gone Incognito");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(initializeNotificationChannel())
                                        {
                                            startHomeActivity();
                                        }
                                    }
                                },2000);
                            }
                            else
                            {
                                Log.d(TAG,"Application is unable to initialize the setup");
                                TvSetupWait.setText("Network Error\nPlease try again later");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        closeApplication();
                                    }
                                },2000);
                            }
                        }
                    }
                }, new Response.ErrorListener()
                    {
                    @Override public void onErrorResponse(VolleyError error)
                        {
                            Log.d(TAG,error.toString());
                            TvSetupWait.setText("Network Error\nPlease try again later\nClosing Application");
                            SetupSpinKit.setVisibility(View.INVISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    closeApplication();
                                }
                            },3000);
                        }
                    });
        RequestQueueSingleton.getInstance(getApplication()).addToRequestQueue(request);
    }

    private void closeApplication()
    {
        finish();
        System.exit(0);
    }

    private void startHomeActivity()
    {
        Intent nextActivityIntent = new Intent(this, HomeActivity.class);
        nextActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(nextActivityIntent);
    }


    private boolean initializeNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "IncogniChat";
            String description = "Notifications for receiving incognichat messages and system events";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("IncogniChat", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        return true;
    }


}
