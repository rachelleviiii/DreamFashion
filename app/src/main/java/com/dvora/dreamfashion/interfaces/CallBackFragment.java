package com.dvora.dreamfashion.interfaces;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public interface CallBackFragment {
    void showFragment(int fragmentID);
    void showFragment(int fragmentID, Bundle bundle);
}
