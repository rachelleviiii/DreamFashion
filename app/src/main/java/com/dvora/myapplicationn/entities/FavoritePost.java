package com.dvora.myapplicationn.entities;

public class FavoritePost {
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
