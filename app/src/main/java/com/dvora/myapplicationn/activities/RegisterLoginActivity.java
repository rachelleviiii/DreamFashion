package com.dvora.myapplicationn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dvora.myapplicationn.R;
import com.dvora.myapplicationn.interfaces.CallBackFragment;

public class RegisterLoginActivity extends AppCompatActivity implements CallBackFragment {

    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);



        navController = Navigation.findNavController(this, R.id.nav_host_register_login_fragment);
    }


    @Override
    public void showActivity(Class activity) {
        Intent intent=new Intent(RegisterLoginActivity.this,activity);
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