package com.dvora.myapplicationn.view_modles;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dvora.myapplicationn.entities.Post;
import com.dvora.myapplicationn.reposetories.Reposetory;

import java.util.List;

public class ProfileViewModel extends ViewModel {




    public LiveData<List<Post>> getAllMyPostLiveData(Context context) {
        return Reposetory.getInstance(context).getAllMyPostsLiveData();
    }
}