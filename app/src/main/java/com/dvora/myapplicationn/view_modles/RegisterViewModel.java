package com.dvora.myapplicationn.view_modles;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.google.firebase.auth.FirebaseAuth;

public class RegisterViewModel extends ViewModel {


    private MutableLiveData<Boolean> mMutableLiveDataResponse = new MutableLiveData<>();
    private String emailAddress;

    public void createNewUser(String email, String pass) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            mMutableLiveDataResponse.postValue(task.isSuccessful());
        });

    }

    public LiveData<Boolean> getLiveDataResponse() {
        return mMutableLiveDataResponse;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String email) {
        emailAddress=email;
    }
}