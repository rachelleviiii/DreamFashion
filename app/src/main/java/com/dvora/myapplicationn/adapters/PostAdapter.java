package com.dvora.myapplicationn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dvora.myapplicationn.R;
import com.dvora.myapplicationn.entities.Post;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder> {
    private List<Post> posts;
    private Context context;

    public PostAdapter(Context context,List<Post> allPosts) {
        this.posts=allPosts;
        this.context=context;
    }


    @NonNull
    @Override
    public PostAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post,null));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapterViewHolder holder, int position) {
        Post post=posts.get(position);

        holder.txtTitle.setText(post.getTitle());
        holder.txtUserName.setText(post.getUserName());

        SimpleDateFormat simpleFormatter=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String timeFormat= simpleFormatter.format(new Date(post.getTimestamp()));
        holder.txtTimePosted.setText(timeFormat);





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


            txtUserName=itemView.findViewById(R.id.txtName);
            imageViewPhoto=itemView.findViewById(R.id.imageViewPhoto);
            imageViewFavorite=itemView.findViewById(R.id.imageViewAddToFavorite);
            txtTimePosted=itemView.findViewById(R.id.txtTime);
            txtTitle=itemView.findViewById(R.id.txtTitle);
        }
    }
}
