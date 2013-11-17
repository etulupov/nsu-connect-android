package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import ru.tulupov.nsuconnect.R;


public class YourGenderSettingsFragment extends BaseSearchSettingsFragment {
    public static YourGenderSettingsFragment newInstance(final Context context) {
        return (YourGenderSettingsFragment) Fragment.instantiate(context, YourGenderSettingsFragment.class.getName());
    }


    @Override
    protected int getTitleTextId() {
        return R.string.search_enter_your_gender;
    }

    @Override
    protected int getItemsArrayId() {
        return R.array.search_your_gender;
    }

    @Override
    protected int getListChoiceMode() {
        return ListView.CHOICE_MODE_SINGLE;
    }
}
