package com.dvora.myapplicationn.view_modles;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Boolean> mutableLiveDataResponse=new MutableLiveData<>();
    private String email;
    public MutableLiveData<Boolean> getMutableLiveDataResponse() {
        return mutableLiveDataResponse;
    }

    public void login(String email, String pass) {
        this.email=email;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mutableLiveDataResponse.postValue(task.isSuccessful());
            }
        });
    }

    public String getEmail() {
        return email;
    }
}