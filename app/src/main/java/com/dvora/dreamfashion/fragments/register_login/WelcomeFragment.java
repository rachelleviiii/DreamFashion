package com.dvora.dreamfashion.fragments.register_login;

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
import android.widget.TextView;

import com.dvora.dreamfashion.R;
import com.dvora.dreamfashion.interfaces.CallBackFragment;
import com.dvora.dreamfashion.view_modles.WellcomeViewModel;

public class WelcomeFragment extends Fragment {

    private WellcomeViewModel mViewModel;
    private CallBackFragment mListener;

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WellcomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button btnSignIn = view.findViewById(R.id.btnSignIn);
        TextView txtSignUp = view.findViewById(R.id.txtSignUp);



        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.showFragment(R.id.navigation_register);
                }
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.showFragment(R.id.navigation_login);
                }
            }
        });
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