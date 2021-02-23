package com.dvora.myapplicationn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.dvora.myapplicationn.R;
import com.dvora.myapplicationn.reposetories.Reposetory;
import com.dvora.myapplicationn.storage.SharePreferenceHelper;

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
                Intent intent;
                if (Reposetory.getInstance(SplashActivity.this).getMyUID() ==null){
                    intent= new Intent(SplashActivity.this, RegisterLoginActivity.class);
                }
                else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}