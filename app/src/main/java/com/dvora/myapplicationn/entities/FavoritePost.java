package com.dvora.myapplicationn.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favorite")
public class FavoritePost {

    @NonNull
    @PrimaryKey
    private String postKey;

    public FavoritePost() { }

    public FavoritePost(String postKey) {
        this.postKey = postKey;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

}
