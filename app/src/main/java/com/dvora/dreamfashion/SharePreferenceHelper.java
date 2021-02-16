package com.dvora.dreamfashion;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceHelper {
    private final String MY_UID="MY_UID";
    private static SharePreferenceHelper instance;
    private final SharedPreferences sharedPreferences;

    public static SharePreferenceHelper getInstance(Context context) {
        if (instance == null)
            instance = new SharePreferenceHelper(context);
        return instance;
    }

    private SharePreferenceHelper(Context context) {
        sharedPreferences= context.getSharedPreferences("app_settings",Context.MODE_PRIVATE);
    }


    public void storeUID(String email){
        sharedPreferences.edit().putString(MY_UID,email).apply();
    }

    public String getMyUID(){
        return sharedPreferences.getString(MY_UID,null);
    }
}
