package com.dvora.myapplicationn.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceHelper {
    private final String MY_UID="MY_UID";
    private final String MY_USER_NAME="MY_USER_NAME";
    private final String LAST_TIMESTAMP_READ_POST="LAST_TIMESTAMP_READ_POST";
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


    public String storeUID(String email){
        String emailToFirebaseKey=email.replaceAll("@","_").replaceAll("\\.","_").replaceAll("\\$","_");
        sharedPreferences.edit().putString(MY_UID,emailToFirebaseKey).commit()  ;
        return emailToFirebaseKey;
    }

    public String getMyUID(){
        return sharedPreferences.getString(MY_UID,null);
    }

    public String getUserName(){
        return sharedPreferences.getString(MY_USER_NAME,null);
    }

    public void resetSP(){
        sharedPreferences.edit().clear().apply();
    }

    public void storeUserName(String name) {
        sharedPreferences.edit().putString(MY_USER_NAME,name).apply();
    }

    public long getLastTimestampUpdatedPost() {
        return sharedPreferences.getLong(LAST_TIMESTAMP_READ_POST,0);
    }
    public void setLastTimestampUpdatedPost(long timestamp) {
        sharedPreferences.edit().putLong(LAST_TIMESTAMP_READ_POST,timestamp).apply();
    }
}
