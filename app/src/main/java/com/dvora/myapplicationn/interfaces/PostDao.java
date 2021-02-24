package com.dvora.myapplicationn.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.dvora.myapplicationn.entities.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPost(List<Post> posts);

    @Query("Delete from Feed where postKey = :key")
    void delete(String key);

    @Query("Select * from Feed order by timestamp desc")
    LiveData <List <Post>> getAllPostsLiveData();

    @Query("Select * from Feed order by timestamp desc limit :limit")
    LiveData <List <Post>> getAllPostsLiveData(int limit);

    @Query("Update Feed set title = :title, imageUrl = :url, timestamp = :time ")
    void updatePost(String title,String url,long time);

}
