package com.archetech.incognichat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.tozny.crypto.android.AesCbcWithIntegrity;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.realm.Realm;

public class SplashScreenActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName() +" ---> ";

    Dialog errorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        if (checkPlayServices()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(Realm.getDefaultInstance().where(Application.class).findFirst()==null)
                    {
                        startSetupActivity();
                    }
                    else
                    {
                        startHomeActivity();
                    }
                }
            },500);
        }
    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {

                if (errorDialog == null) {
                    errorDialog = googleApiAvailability.getErrorDialog(this, resultCode, 2404);
                    errorDialog.setCancelable(false);
                }

                if (!errorDialog.isShowing())
                    errorDialog.show();

            }
        }

        return resultCode == ConnectionResult.SUCCESS;
    }

    private void startSetupActivity()
    {
        Intent nextActivityIntent = new Intent(this, SetupActivity.class);
        nextActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(nextActivityIntent);
    }

    private void startHomeActivity()
    {
        Intent nextActivityIntent = new Intent(this, HomeActivity.class);
        nextActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(nextActivityIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPlayServices()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(Realm.getDefaultInstance().where(Application.class).findFirst()==null)
                    {
                        startSetupActivity();
                    }
                    else
                    {
                        startHomeActivity();
                    }
                }
            },500);
        }

//        try {
//
//            String salt = AesCbcWithIntegrity.saltString(AesCbcWithIntegrity.generateSalt());
//            AesCbcWithIntegrity.SecretKeys keys = AesCbcWithIntegrity.generateKeyFromPassword("abcd1234",salt);
//            AesCbcWithIntegrity.CipherTextIvMac cipherTextIvMac = AesCbcWithIntegrity.encrypt("some test",keys);
//            String ciphertextString = cipherTextIvMac.toString();
//            Log.d("--->",ciphertextString);
//
//            keys = AesCbcWithIntegrity.generateKeyFromPassword("abcd1234",salt);
//            cipherTextIvMac = new AesCbcWithIntegrity.CipherTextIvMac(ciphertextString);
//            String plainText = AesCbcWithIntegrity.decryptString(cipherTextIvMac, keys);
//
//            Log.d("--->",plainText);
//
//        } catch (GeneralSecurityException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }
}
