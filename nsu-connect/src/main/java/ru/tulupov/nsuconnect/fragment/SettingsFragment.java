package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class SettingsFragment extends WelcomeFragment {
    public static SettingsFragment newInstance(final Context context) {
        return (SettingsFragment) Fragment.instantiate(context, SettingsFragment.class.getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    protected void navigate(Page page) {
        if (page == Page.FINISH) {
            saveSettings();
            closeFragment();
            return;
        }
        super.navigate(page);
    }
}
