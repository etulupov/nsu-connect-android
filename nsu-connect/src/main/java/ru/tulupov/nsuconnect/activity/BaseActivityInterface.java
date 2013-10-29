package ru.tulupov.nsuconnect.activity;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import ru.tulupov.nsuconnect.fragment.BaseFragment;

public interface BaseActivityInterface {
    void addFragment(BaseFragment fragment);
    void showFragment(BaseFragment fragment);
    void showDialog(DialogFragment fragment);
    void closeFragment();
    SlidingMenu getSlidingMenu();
    Activity getActivity();
}
