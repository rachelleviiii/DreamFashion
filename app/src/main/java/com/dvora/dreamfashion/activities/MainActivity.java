package com.dvora.dreamfashion.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.dvora.dreamfashion.R;
import com.dvora.dreamfashion.storage.SharePreferenceHelper;
import com.dvora.dreamfashion.entities.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        String test = "new test";


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        User user = new User("Daniel", "daniel@gmail.com");


        User user2 = new User("Dvora", "dvora@gmail.com");

        myRef.child("users_list").push().setValue(user);
        myRef.child("users_list").push().setValue(user2);


        String myUID = SharePreferenceHelper.getInstance(this).getMyUID();

        Toast.makeText(this, myUID, Toast.LENGTH_SHORT).show();

    }

}