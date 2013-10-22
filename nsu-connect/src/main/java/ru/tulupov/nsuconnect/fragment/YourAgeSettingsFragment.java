package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import ru.tulupov.nsuconnect.R;


public class YourAgeSettingsFragment extends SearchSettingsFragment {
    public static YourAgeSettingsFragment newInstance(final Context context) {
        return (YourAgeSettingsFragment) Fragment.instantiate(context, YourAgeSettingsFragment.class.getName());
    }


    @Override
    protected int getTitleTextId() {
        return R.string.search_enter_your_age;
    }

    @Override
    protected int getItemsArrayId() {
        return R.array.search_your_age;
    }

    @Override
    protected int getListChoiceMode() {
        return ListView.CHOICE_MODE_SINGLE;
    }
}
