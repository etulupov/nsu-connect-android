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
import ru.tulupov.nsuconnect.activity.MainActivity;
import ru.tulupov.nsuconnect.adapter.PagerAdapter;
import ru.tulupov.nsuconnect.helper.SettingsHelper;
import ru.tulupov.nsuconnect.model.SearchParameters;
import ru.tulupov.nsuconnect.model.Settings;
import ru.tulupov.nsuconnect.widget.CustomViewPager;


public class ConversationsFragment extends BaseFragment {


    public static ConversationsFragment newInstance(final Context context) {
        return (ConversationsFragment) Fragment.instantiate(context, ConversationsFragment.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fgt_welcome, container, false);
    }

    @Override
    public int getTitleId() {
        return R.string.conversations_title;
    }
    @Override
    public int getMenuItemId() {
        return R.id.menu_converstions;
    }
}
