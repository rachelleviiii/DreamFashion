package com.dvora.myapplicationn.fragments;

import android.app.AlertDialog;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dvora.myapplicationn.R;
import com.dvora.myapplicationn.adapters.PostAdapter;
import com.dvora.myapplicationn.entities.Post;
import com.dvora.myapplicationn.interfaces.ICallbackAdapter;
import com.dvora.myapplicationn.view_modles.ProfileViewModel;

import java.util.List;

public class ProfileFragment extends BaseFragment implements ICallbackAdapter {

    private ProfileViewModel profileViewModel;
    private RecyclerView recyclerViewPost;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        return root;
    }


    private void loadObserverNewPost() {

        if (profileViewModel == null)
            return;
        profileViewModel.getAllMyPostLiveData(getContext()).observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                PostAdapter postAdapter = new PostAdapter(getContext(), posts, true, ProfileFragment.this);
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
        }, 1000);
    }

    private void loadViews(View view) {
        recyclerViewPost = view.findViewById(R.id.recyclerViewPost);
        recyclerViewPost.setHasFixedSize(true);
        recyclerViewPost.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    @Override
    public void onItemClicked(Post post) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("post", post);
        mListener.showFragment(R.id.navigation_create_post, bundle);
    }
}