package com.dvora.myapplicationn.view_modles;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.dvora.myapplicationn.entities.Post;
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

    private Post post;
    private String lastImageURL;
    public Post getPost() {
        return post;
    }

    public void initPost(Bundle bundle) {
        post= (Post) bundle.getSerializable("post");
        if (post != null) {
            lastImageURL=post.getImageUrl();
        }
    }

    public void updatePost(Context context) {
        Reposetory.getInstance(context).updatePost(post,imageUri,lastImageURL);
    }

    public void deletePost(Context context) {
        Reposetory.getInstance(context).deletePost(post);
    }
    // TODO: Implement the ViewModel
}