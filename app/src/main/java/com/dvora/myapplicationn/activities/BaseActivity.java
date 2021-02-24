package com.dvora.myapplicationn.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import com.dvora.myapplicationn.interfaces.CallBackFragment;

public abstract class BaseActivity extends AppCompatActivity implements CallBackFragment {
    NavController navController;
    @Override
    public void showActivity(Class activity) {
        Intent intent=new Intent(BaseActivity.this,activity);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFragment(int fragmentID) {
        navController.navigate(fragmentID);
    }

    @Override
    public void showFragment(int fragmentID, Bundle bundle) {
        navController.navigate(fragmentID,bundle);
    }
}
