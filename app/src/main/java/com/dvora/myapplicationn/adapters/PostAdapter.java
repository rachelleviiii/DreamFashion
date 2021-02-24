package com.dvora.myapplicationn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dvora.myapplicationn.R;
import com.dvora.myapplicationn.entities.Post;
import com.dvora.myapplicationn.interfaces.ICallbackAdapter;
import com.dvora.myapplicationn.interfaces.IImageDownload;
import com.dvora.myapplicationn.reposetories.Reposetory;
import com.google.firebase.database.core.Repo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder> {
    private List<Post> posts;
    private Context context;
    private boolean onlyImage;
    private ICallbackAdapter iCallbackAdapter;

    public PostAdapter(Context context, List<Post> allPosts,boolean onlyImage) {
        this.posts = allPosts;
        this.onlyImage = onlyImage;
        this.context = context;
    }

    public PostAdapter(Context context, List<Post> allPosts, boolean onlyImage, ICallbackAdapter iCallbackAdapter) {
        this.iCallbackAdapter =iCallbackAdapter ;
        this.posts = allPosts;
        this.onlyImage = onlyImage;
        this.context = context;
    }


    @NonNull
    @Override
    public PostAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int res=onlyImage?R.layout.item_post:R.layout.item_pos_regular;
        return new PostAdapterViewHolder(LayoutInflater.from(context).inflate(res, null));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapterViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.txtTitle.setText(post.getTitle());
        holder.txtUserName.setText(post.getUserName());

        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String timeFormat = simpleFormatter.format(new Date(post.getTimestamp()));
        holder.txtTimePosted.setText(timeFormat);

        Reposetory.getInstance(context).getImage(post.getImageUrl(), new IImageDownload() {
            @Override
            public void onImageDownloaded(File file) {
                Glide.with(context).load(file).centerInside().into(holder.imageViewPhoto);
            }
        });


        holder.imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reposetory.getInstance(context).insertToMyFavorite(post);
                Toast.makeText(context, "Add to favorite", Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iCallbackAdapter!=null){
                    iCallbackAdapter.onItemClicked(post);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewPhoto;
        private ImageView imageViewFavorite;
        private TextView txtTimePosted;
        private TextView txtUserName;
        private TextView txtTitle;


        public PostAdapterViewHolder(@NonNull View itemView) {
            super(itemView);


            txtUserName = itemView.findViewById(R.id.txtName);
            imageViewPhoto = itemView.findViewById(R.id.imageViewPhoto);
            imageViewFavorite = itemView.findViewById(R.id.imageViewAddToFavorite);
            txtTimePosted = itemView.findViewById(R.id.txtTime);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
