package com.dvora.myapplicationn.storage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dvora.myapplicationn.entities.FavoritePost;
import com.dvora.myapplicationn.entities.Post;
import com.dvora.myapplicationn.interfaces.FavoriteDao;
import com.dvora.myapplicationn.interfaces.PostDao;

@Database(entities = {Post.class, FavoritePost.class}, version =47, exportSchema = false)
public abstract class DatabasePost extends RoomDatabase {
    private static DatabasePost instance;
    public abstract PostDao postDao();
    public abstract FavoriteDao favoriteDao();
    public static DatabasePost getInstance(Context context) {
        if (instance == null) {
            instance =  Room.databaseBuilder(context, DatabasePost.class, "DREAM_FASHION_DATABASE").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }

}
