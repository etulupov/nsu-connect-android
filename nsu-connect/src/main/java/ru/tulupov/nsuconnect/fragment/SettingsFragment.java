package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.adapter.PagerAdapter;
import ru.tulupov.nsuconnect.helper.SettingsHelper;
import ru.tulupov.nsuconnect.model.SearchParameters;
import ru.tulupov.nsuconnect.model.Settings;
import ru.tulupov.nsuconnect.widget.CustomViewPager;


public class SettingsFragment extends WelcomeFragment {
    public static SettingsFragment newInstance(final Context context) {
        return (SettingsFragment) Fragment.instantiate(context, SettingsFragment.class.getName());
    }

    @Override
    protected void navigate(int page) {
        if (page == PAGE_FINISH) {
            saveSettings();
            closeFragment();
            return;
        }
        super.navigate(page);
    }
}
