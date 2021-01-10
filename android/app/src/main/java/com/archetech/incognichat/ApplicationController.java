package com.archetech.incognichat;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmObject;

public class ApplicationController {

    public final String TAG = this.getClass().getSimpleName();
    Context ctx;
    Realm db;
    Application application;

    public ApplicationController(Context _ctx)
    {
        ctx = _ctx;
        Realm.init(ctx);
        db = Realm.getDefaultInstance();
        application = db.where(Application.class).findFirst();
    }

    public boolean isInitialized()
    {
        if(application!=null)
            return true;
        return false;
    }

    public String getPrivateKey()
    {
        return application.getPrivate_key();
    }

    public Map<String, String> getAuthenticationHeader()
    {
        try
        {
            Log.d(TAG,"access_token granted: "+application.getAccess_token());
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " +    application.getAccess_token());
            return headers;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
    }

    public String getPublicKey()
    {
        return application.getPublic_key();
    }

    public String getAccessToken()
    {
        return application.getAccess_token();
    }

    public String getSyncedAt()
    {
        return application.getSynced_at();
    }

    public String getLink()
    {
        return application.getHandle();
    }

    public boolean InitializeApplication(String _nickname, String _access_token, String _version, String _handle, String _public_key, String _private_key, String _recovery_pin, String _reputation, String _views, String _badges_created)
    {
        try
        {
            db.beginTransaction();
            application = db.createObject(Application.class);
            application.setAccess_token(_access_token);
            application.setVersion(_version);
            application.setCreated_at();
            application.setHandle(_handle);
            application.setPublic_key(_public_key);
            application.setPrivate_key(_private_key);
            application.setRecovery_pin(_recovery_pin);
            application.setReputation(Integer.parseInt(_reputation));
            application.setViews(Integer.parseInt(_views));
            application.setBadges_created(Integer.parseInt(_badges_created));
            application.setSynced_at();
            db.commitTransaction();

            return true;
        }
        catch (Exception ex)
        {
            Log.d(TAG,ex.toString());
            ex.printStackTrace();
            return false;
        }
    }

    public boolean syncProfile(String _reputation, String  _views)
    {
        try
        {
            db.beginTransaction();
            application = db.where(Application.class).findFirst();
            application.setReputation(Long.parseLong(_reputation));
            application.setViews(Long.parseLong(_views));
            application.setSynced_at();
            db.commitTransaction();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public String getReputation()
    {
        return String.valueOf(application.getReputation());
    }

    public String getViews()
    {
        return String.valueOf(application.getViews());
    }



}
