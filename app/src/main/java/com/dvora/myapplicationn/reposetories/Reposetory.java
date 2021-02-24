package com.dvora.myapplicationn.reposetories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dvora.myapplicationn.entities.Post;
import com.dvora.myapplicationn.entities.User;
import com.dvora.myapplicationn.storage.SharePreferenceHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Reposetory {
    private static Reposetory instance;
    private DatabaseReference myRef;
    private SharePreferenceHelper preferenceHelper;
    private String myUID;
    private StorageReference refStorage;

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

        startListenerNewPosts();
        //TODO init DB
    }

    private void startListenerNewPosts() {
        long lastTimestampUpdatedPost = preferenceHelper.getLastTimestampUpdatedPost();
        myRef.child(FEED_TABLE).startAt(lastTimestampUpdatedPost).orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
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
                }

                //Insert to DB

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getMyUID() {
        return myUID;
    }

    private final String USER_TABLE = "users";
    private final String FEED_TABLE = "feed";

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

    public void createNewPost(String title, Uri imageUri) {

        Post post = new Post(myUID, title, System.currentTimeMillis());
        String postKy = myRef.child(FEED_TABLE).push().getKey();
        post.setPostKey(postKy);
        myRef.child(FEED_TABLE).child(postKy).setValue(post);

        refStorage.child(post.getImageUrl()).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
}
