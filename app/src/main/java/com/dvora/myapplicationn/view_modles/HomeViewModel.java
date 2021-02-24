package com.dvora.myapplicationn.view_modles;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dvora.myapplicationn.R;
import com.dvora.myapplicationn.entities.Post;
import com.dvora.myapplicationn.reposetories.Reposetory;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {


    private final Reposetory reposetory;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        reposetory=  Reposetory.getInstance(application);

    }


    public LiveData<List<Post>> getAllPostLiveData(){
        return reposetory.getAllPostsLiveData();
    }
}