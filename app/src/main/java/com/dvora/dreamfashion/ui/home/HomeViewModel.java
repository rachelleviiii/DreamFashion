package com.dvora.dreamfashion.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dvora.dreamfashion.R;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;


    public HomeViewModel(@NonNull Application application) {
        super(application);

        mText = new MutableLiveData<>();
        mText.setValue(application.getResources().getString(R.string.app_name));
    }


    public LiveData<String> getText() {
        return mText;
    }
}