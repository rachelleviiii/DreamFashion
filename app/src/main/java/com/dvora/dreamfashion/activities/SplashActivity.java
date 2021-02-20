package com.dvora.dreamfashion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.dvora.dreamfashion.R;
import com.dvora.dreamfashion.storage.SharePreferenceHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        showScreen();
    }

    private void showScreen() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO check witch screen to present
                Intent intent;
                if (SharePreferenceHelper.getInstance(SplashActivity.this).getMyUID()==null){
                    intent= new Intent(SplashActivity.this, RegisterLoginActivity.class);
                }
                else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
            }
        }, 3000);
    }
}