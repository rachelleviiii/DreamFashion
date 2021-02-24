package com.dvora.myapplicationn.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dvora.myapplicationn.R;
import com.dvora.myapplicationn.interfaces.IImageDownload;
import com.dvora.myapplicationn.reposetories.Reposetory;
import com.dvora.myapplicationn.view_modles.CreatePostViewModel;
import com.google.firebase.database.core.Repo;

import java.io.File;

public class CreatePostFragment extends BaseFragment {

    private static final int REQUEST_STORAGE_ID = 20;
    private static final int RESULT_FROM_GALLERY = 55;
    private CreatePostViewModel mViewModel;
    private TextView txtName;
    private ImageView imagePost;
    private EditText edtTitle;
    private Button btnShare;
    private Button btnDelete;

    public static CreatePostFragment newInstance() {
        return new CreatePostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_post_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreatePostViewModel.class);
        // TODO: Use the ViewModel

        Bundle bundle = getArguments();
        if (bundle != null) {
            mViewModel.initPost(bundle);
        }

        if (mViewModel.getPost() != null) {
            loadData();
            btnShare.setText("Update");
        } else {
            btnShare.setText("Share");
        }
        btnDelete.setVisibility(mViewModel.getPost() != null ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadViews(view);
        loadListeners();



    }


    private void loadData() {

        Reposetory.getInstance(getContext()).getImage(mViewModel.getPost().getImageUrl(), new IImageDownload() {
            @Override
            public void onImageDownloaded(File file) {
                Glide.with(getContext()).load(file).centerInside().into(imagePost);

            }
        });

        edtTitle.setText(mViewModel.getPost().getTitle());
    }

    private void loadListeners() {

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.deletePost(getContext());


                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListener.showFragment(R.id.navigation_home);

                    }
                }, 500);
            }
        });
        imagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = edtTitle.getText().toString();

                if (mViewModel.getPost() == null) {
                    mViewModel.createNewPost(title, getContext());
                } else {
                    mViewModel.getPost().setTimestamp(System.currentTimeMillis());
                    mViewModel.getPost().setTitle(title);
                    mViewModel.updatePost(getContext());

                }


                mListener.showFragment(R.id.navigation_home);
            }
        });
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                askPermission();
            } else {
                getImageFromGallery();
            }
        }
    }

    private void askPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_ID);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean isPermissionOK = true;
        if (requestCode == REQUEST_STORAGE_ID) {
            for (int res : grantResults) {
                if (res != PackageManager.PERMISSION_GRANTED) {
                    isPermissionOK = false;
                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            if (isPermissionOK) {
                getImageFromGallery();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            mViewModel.setImageUri(data.getData());
//            imagePost.setImageURI(mViewModel.getImageUri());

            Glide.with(this).load(mViewModel.getImageUri()).centerInside().into(imagePost);
        }
    }

    private void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_FROM_GALLERY);
        Toast.makeText(getContext(), "My code", Toast.LENGTH_SHORT).show();
    }

    private void loadViews(View view) {


        btnDelete = view.findViewById(R.id.btnDelete);
        txtName = view.findViewById(R.id.txtName);
        imagePost = view.findViewById(R.id.imageViewPost);
        edtTitle = view.findViewById(R.id.edtTitle);
        btnShare = view.findViewById(R.id.btnShare);


    }

}