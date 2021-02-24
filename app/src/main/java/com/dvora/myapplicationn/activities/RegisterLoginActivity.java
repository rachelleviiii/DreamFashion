package com.dvora.myapplicationn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.dvora.myapplicationn.R;

public class RegisterLoginActivity extends BaseActivity {

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);



        navController = Navigation.findNavController(this, R.id.nav_host_register_login_fragment);
    }



}