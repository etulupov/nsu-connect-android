package ru.tulupov.nsuconnect.fragment;

import android.support.v4.app.Fragment;


public abstract class BaseFragment extends Fragment {

    public int getTitleId() {
        return 0;
    }
    public int getMenuItemId() {
        return 0;
    }
    public boolean onBackPressed() {
        return false;
    }
}
