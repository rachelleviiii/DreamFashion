package com.dvora.myapplicationn.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dvora.myapplicationn.R;
import com.dvora.myapplicationn.adapters.PostAdapter;
import com.dvora.myapplicationn.entities.Post;
import com.dvora.myapplicationn.view_modles.HomeViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerViewPost;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    private void loadObserverNewPost() {

        homeViewModel.getAllPostLiveData().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                PostAdapter  postAdapter=new PostAdapter(getContext(),posts,false);
                recyclerViewPost.setAdapter(postAdapter);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        loadViews(view);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadObserverNewPost();

            }
        },1000);
    }

    private void loadViews(View view) {
        recyclerViewPost= view.findViewById(R.id.recyclerViewPost);
        recyclerViewPost.setHasFixedSize(true);
        recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}