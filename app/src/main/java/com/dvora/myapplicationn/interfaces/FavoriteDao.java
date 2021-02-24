package com.dvora.myapplicationn.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dvora.myapplicationn.entities.FavoritePost;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoritePost(FavoritePost favoritePost);

    @Query("Delete from Favorite where postKey = :key")
    void delete(String key);

    @Query("Select * from Favorite")
    LiveData<List<FavoritePost>> getAllFavoritePostsLiveData();


    @Query("Delete from Favorite")
    void deleteAll();
}
