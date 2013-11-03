package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import ru.tulupov.nsuconnect.R;


public class YourUniversitySettingsFragment extends SearchSettingsFragment {
    public static YourUniversitySettingsFragment newInstance(final Context context) {
        return (YourUniversitySettingsFragment) Fragment.instantiate(context, YourUniversitySettingsFragment.class.getName());
    }


    @Override
    protected int getTitleTextId() {
        return R.string.search_enter_your_university;
    }

    @Override
    protected int getItemsArrayId() {
        return R.array.search_your_university;
    }

    @Override
    protected int getListChoiceMode() {
        return ListView.CHOICE_MODE_SINGLE;
    }
}
