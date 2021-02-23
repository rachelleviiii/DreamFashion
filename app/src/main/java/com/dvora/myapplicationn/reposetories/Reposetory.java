package com.dvora.myapplicationn.reposetories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dvora.myapplicationn.entities.User;
import com.dvora.myapplicationn.storage.SharePreferenceHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Reposetory {
    private static Reposetory instance;
    private DatabaseReference myRef;
    private SharePreferenceHelper preferenceHelper;
    private String myUID;

    public static Reposetory getInstance(Context context) {
        if (instance == null)
            instance = new Reposetory(context);
        return instance;
    }

    private Reposetory(Context context) {
        myRef = FirebaseDatabase.getInstance().getReference();
        preferenceHelper = SharePreferenceHelper.getInstance(context);
        myUID=preferenceHelper.getMyUID();
        //TODO init DB
    }

    public  String getMyUID(){
        return myUID;
    }
    private final  String USER_TABLE="users";

    public void insertNewUser(String emailAddress, String name) {
        myUID= preferenceHelper.storeUID(emailAddress);
        preferenceHelper.storeUserName(name);
        User user=new User(name,emailAddress);
        myRef.child(USER_TABLE).child(preferenceHelper.getMyUID()).setValue(user);
    }

    public void loadMyUser(String email) {
        myUID=preferenceHelper.storeUID(email);
        myRef.child(USER_TABLE).child(preferenceHelper.getMyUID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                preferenceHelper.storeUserName(user.getName());
                Log.d("gsedgtsdg",user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
