package com.dvora.myapplicationn.entities;

public class Post {
    private String userId;
    private String postKey;
    private String title;
    private String imageUrl;
    private long timestamp;

    public Post(String userId, String postKey, String title, String imageUrl, long timestamp) {
        this.userId = userId;
        this.postKey = postKey;
        this.title = title;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
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
