package com.dvora.myapplicationn.view_modles;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.dvora.myapplicationn.reposetories.Reposetory;

public class CreatePostViewModel extends ViewModel {
    private Uri imageUri;

    public void setImageUri(Uri data) {
        imageUri =data;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void createNewPost(String title, Context context) {
        Reposetory.getInstance(context).createNewPost(title, imageUri);
    }
    // TODO: Implement the ViewModel
}