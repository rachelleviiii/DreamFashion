package com.dvora.myapplicationn.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Feed")
public class Post {
    @NonNull
    @PrimaryKey
    private String userId;
    private String postKey;
    private String title;
    private String imageUrl;
    private long timestamp;
    private String userName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public Post(String userId, String title, long timestamp,String userName) {
        this.userId = userId;
         this.title = title;
        this.timestamp = timestamp;
        this.userName=userName;
        setImageUrl(userId+"_"+timestamp+".jpg");
    }

    public Post(@NonNull String userId, String postKey, String title, String imageUrl, long timestamp, String userName) {
        this.userId = userId;
        this.postKey = postKey;
        this.title = title;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
        this.userName = userName;
    }

    public Post(){ }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
