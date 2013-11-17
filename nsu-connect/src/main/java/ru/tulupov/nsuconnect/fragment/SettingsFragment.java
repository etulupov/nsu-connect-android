package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class SettingsFragment extends WelcomeFragment {
    public static SettingsFragment newInstance(final Context context) {
        return (SettingsFragment) Fragment.instantiate(context, SettingsFragment.class.getName());
    }

    public SettingsFragment() {
        super(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

}
