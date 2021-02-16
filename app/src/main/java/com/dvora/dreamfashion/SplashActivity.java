package com.dvora.dreamfashion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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

                Intent intent= new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}