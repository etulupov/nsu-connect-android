package ru.tulupov.nsuconnect.activity;

import android.support.v4.app.Fragment;

import ru.tulupov.nsuconnect.fragment.BaseFragment;

public interface BaseActivityInterface {
    void addFragment(BaseFragment fragment);
    void showFragment(BaseFragment fragment);
}
