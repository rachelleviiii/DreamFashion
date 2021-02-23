package com.dvora.myapplicationn.fragments.register_login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dvora.myapplicationn.R;
import com.dvora.myapplicationn.activities.MainActivity;
import com.dvora.myapplicationn.interfaces.CallBackFragment;
import com.dvora.myapplicationn.reposetories.Reposetory;
import com.dvora.myapplicationn.view_modles.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    private EditText edtPass,edtEmail;
    private Button btnLogin;
    private CallBackFragment mListener;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel

        observerResponseLogin();
    }

    private void observerResponseLogin() {
        mViewModel.getMutableLiveDataResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoginSuccessfully) {
                if (isLoginSuccessfully) {
                    Reposetory.getInstance(getContext()).loadMyUser(mViewModel.getEmail());
                    mListener.showActivity(MainActivity.class);
                } else
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadViews(view);
        loadListenerClick();
    }

    private void loadListenerClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =edtEmail.getText().toString() ;
                String pass =edtPass.getText().toString() ;
                mViewModel.login(email,pass);
            }
        });
    }



    private void loadViews(View view) {
        edtPass=view.findViewById(R.id.edtPass);
        edtEmail=view.findViewById(R.id.edtEmail);
        btnLogin=view.findViewById(R.id.btnLogin);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CallBackFragment){
            mListener= (CallBackFragment) context;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }
}