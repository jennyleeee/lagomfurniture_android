package com.example.lagomfurniture.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {

    private SharedPreferences sharedPreferences;
    private Context context;
    private static final String AUTO_LOGIN_SHARED = "user";

    public SharedPreferencesUtils(Context context) {
        this.context = context;
    }

    public String getStringSharedPreferences(String key) {
        sharedPreferences = context.getSharedPreferences(AUTO_LOGIN_SHARED, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public int getIntSharedPreferences(String key) {
        sharedPreferences = context.getSharedPreferences(AUTO_LOGIN_SHARED, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key,0);
    }

    public void setStringSharedPreferences(String key, String auto_login_info) {
        sharedPreferences = context.getSharedPreferences(AUTO_LOGIN_SHARED, Activity.MODE_PRIVATE);
        SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
        sharedEditor.putString(key, auto_login_info);
        sharedEditor.apply();
    }

    public void setIntSharedPreferences(String key, int auto_login_info) {
        sharedPreferences = context.getSharedPreferences(AUTO_LOGIN_SHARED, Activity.MODE_PRIVATE);
        SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
        sharedEditor.putInt(key, auto_login_info);
        sharedEditor.apply();
    }

    public void deleteSharedPreferences(String key) {
        sharedPreferences = context.getSharedPreferences(AUTO_LOGIN_SHARED, Activity.MODE_PRIVATE);
        SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
        sharedEditor.remove(key);
        sharedEditor.apply();
    }

    public void clearSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(AUTO_LOGIN_SHARED, Activity.MODE_PRIVATE);
        SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
        sharedEditor.clear();
        sharedEditor.apply();
    }
}
