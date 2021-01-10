package com.archetech.incognichat;

import android.app.ActivityManager;
import android.content.Context;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Helper {

    public static String getValueFromJSONObject(JSONObject _response, String _key)
    {
        try
        {
            return _response.getString(_key);
        }
        catch (JSONException exception)
        {
            exception.printStackTrace();
            return null;
        }
    }

    public static String getValueFromRemoteMessage(RemoteMessage _message, String _key)
    {
        try
        {
            return _message.getData().get(_key);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
    }



    public static  boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = "com.archetech.incognichat";
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
