package com.dvora.myapplicationn.fragments;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dvora.myapplicationn.interfaces.CallBackFragment;

public class BaseFragment extends Fragment {
    protected CallBackFragment mListener;

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
