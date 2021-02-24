package com.dvora.myapplicationn.view_modles;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dvora.myapplicationn.entities.FavoritePost;
import com.dvora.myapplicationn.entities.Post;
import com.dvora.myapplicationn.reposetories.Reposetory;

import java.util.ArrayList;
import java.util.List;

public class FavoriteViewModel extends ViewModel {


    public LiveData<List<FavoritePost>> getAllFavoritePostLiveData(Context context) {
        return Reposetory.getInstance(context).getAllFavoritePostsLiveData();
    }

    private List<Post> allPosts=new ArrayList<>();
    public void clearPost() {
        allPosts.clear();
    }
    public void addPost(Post post){
        allPosts.add(post);
    }

    public List<Post> getPosts() {
        return allPosts;
    }

    public Post loadPost(String postKey,Context context) {
        return Reposetory.getInstance(context).getPostFromKey(postKey);
    }
}