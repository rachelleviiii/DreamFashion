package com.dvora.myapplicationn.reposetories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.dvora.myapplicationn.entities.FavoritePost;
import com.dvora.myapplicationn.entities.Post;
import com.dvora.myapplicationn.entities.User;
import com.dvora.myapplicationn.interfaces.FavoriteDao;
import com.dvora.myapplicationn.interfaces.IImageDownload;
import com.dvora.myapplicationn.interfaces.PostDao;
import com.dvora.myapplicationn.storage.DatabasePost;
import com.dvora.myapplicationn.storage.SharePreferenceHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reposetory {
    private static Reposetory instance;
    private DatabaseReference myRef;
    private SharePreferenceHelper preferenceHelper;
    private String myUID;
    private StorageReference refStorage;
    private PostDao postDao;
    private FavoriteDao favoriteDao;

    public static Reposetory getInstance(Context context) {
        if (instance == null)
            instance = new Reposetory(context);
        return instance;
    }

    private Reposetory(Context context) {
        myRef = FirebaseDatabase.getInstance().getReference();
        refStorage = FirebaseStorage.getInstance().getReference();
        preferenceHelper = SharePreferenceHelper.getInstance(context);
        myUID = preferenceHelper.getMyUID();
        postDao = DatabasePost.getInstance(context).postDao();
        favoriteDao = DatabasePost.getInstance(context).favoriteDao();
        startListenerNewPostsFromFirebase();
        //TODO init DB
    }

    public LiveData<List<Post>> getAllPostsLiveData() {
        return postDao.getAllPostsLiveData();
    }

    public LiveData<List<Post>> getAllMyPostsLiveData() {
        return postDao.getAllMyPostsLiveData(myUID);
    }

    public LiveData<List<FavoritePost>> getAllFavoritePostsLiveData() {
        return favoriteDao.getAllFavoritePostsLiveData();
    }

    public LiveData<List<Post>> getAllPostsLiveDataLimit(int limitPosts) {
        return postDao.getAllPostsLiveData(limitPosts);
    }

    private void startListenerNewPostsFromFirebase() {
        long lastTimestampUpdatedPost = preferenceHelper.getLastTimestampUpdatedPost();
        myRef.child(FEED_TABLE).startAt(lastTimestampUpdatedPost + 1).orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Post> posts = new ArrayList<>();

                for (DataSnapshot item : snapshot.getChildren()) {
                    Post post = item.getValue(Post.class);
                    posts.add(post);
                }

                if (posts.size() > 0) {
                    long lastTimestamp = posts.get(posts.size() - 1).getTimestamp();
                    preferenceHelper.setLastTimestampUpdatedPost(lastTimestamp);
                    postDao.insertPost(posts);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getMyUID() {
        return myUID;
    }

    public Post getPostFromKey(String key) {
        return postDao.getPostByKey(key);
    }

    private final String USER_TABLE = "users";
    private final String FEED_TABLE = "feed";
    private final String FAVORITE_TABLE = "favorite";

    public void insertNewUser(String emailAddress, String name) {
        myUID = preferenceHelper.storeUID(emailAddress);
        preferenceHelper.storeUserName(name);
        User user = new User(name, emailAddress);
        myRef.child(USER_TABLE).child(preferenceHelper.getMyUID()).setValue(user);
    }

    public void loadMyUser(String email) {
        myUID = preferenceHelper.storeUID(email);
        myRef.child(USER_TABLE).child(preferenceHelper.getMyUID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                preferenceHelper.storeUserName(user.getName());
                Log.d("gsedgtsdg", user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updatePost(Post post, Uri imageUri, String lastImageURL) {

        post.setImageUrl(myUID + "_" + post.getTimestamp() + ".jpg");

        postDao.updatePost(post.getTitle(),post.getImageUrl(), post.getTimestamp());

        refStorage.child(lastImageURL).delete();
        uploadImage(post.getImageUrl(),imageUri);

    }

    public void createNewPost(String title, Uri imageUri) {

        Post post = new Post(myUID, title, System.currentTimeMillis(), preferenceHelper.getUserName());
        String postKy = myRef.child(FEED_TABLE).push().getKey();
        post.setPostKey(postKy);
        myRef.child(FEED_TABLE).child(postKy).setValue(post);

        favoriteDao.insertFavoritePost(new FavoritePost(postKy));

        uploadImage(post.getImageUrl(), imageUri);
    }

    private void uploadImage(String imageUrl, Uri imageUri) {
        refStorage.child(imageUrl).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }

        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void getImage(String imageUrl, IImageDownload imageDownload) {
        File file = null;
        try {
            file = File.createTempFile("image", ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File finalFile = file;
        refStorage.child(imageUrl).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                if (imageDownload != null)
                    imageDownload.onImageDownloaded(finalFile);
            }
        });
    }

    public void cleaFeedrData() {
        preferenceHelper.setLastTimestampUpdatedPost(0);
        postDao.deleteAll();
    }

    public void insertToMyFavorite(Post post) {
        FavoritePost favoritePost = new FavoritePost(post.getPostKey());
        myRef.child(FAVORITE_TABLE).child(myUID).child(post.getPostKey()).setValue(favoritePost);
        favoriteDao.insertFavoritePost(favoritePost);
    }

    public void clearFavoriteList() {
        favoriteDao.deleteAll();
    }


    public void deletePost(Post post) {
        myRef.child(FEED_TABLE).child(post.getPostKey()).removeValue();
        myRef.child(FAVORITE_TABLE).child(myUID).child(post.getPostKey()).removeValue();
        refStorage.child(post.getImageUrl()).delete();
        postDao.delete(post.getPostKey());
        favoriteDao.delete(post.getPostKey());
    }

    public String getUserName() {
        return preferenceHelper.getUserName();
    }
}
