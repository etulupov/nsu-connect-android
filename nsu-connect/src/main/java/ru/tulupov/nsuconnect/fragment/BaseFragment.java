package ru.tulupov.nsuconnect.fragment;

import android.support.v4.app.Fragment;


public abstract class BaseFragment extends Fragment {
    public boolean onBackPressed() {
        return false;
    }
}
