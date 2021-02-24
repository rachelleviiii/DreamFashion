package com.dvora.myapplicationn.entities;

public class Post {
    private String userId;
    private String postKey;
    private String title;
    private String imageUrl;
    private long timestamp;

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

    public Post(String userId, String title, long timestamp) {
        this.userId = userId;
         this.title = title;
        this.timestamp = timestamp;
        setImageUrl(userId+"_"+timestamp+".jpg");
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
