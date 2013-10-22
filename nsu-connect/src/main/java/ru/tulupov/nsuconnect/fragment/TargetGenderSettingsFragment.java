package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import ru.tulupov.nsuconnect.R;


public class TargetGenderSettingsFragment extends SearchSettingsFragment {
    public static TargetGenderSettingsFragment newInstance(final Context context) {
        return (TargetGenderSettingsFragment) Fragment.instantiate(context, TargetGenderSettingsFragment.class.getName());
    }


    @Override
    protected int getTitleTextId() {
        return R.string.search_enter_target_gender;
    }

    @Override
    protected int getItemsArrayId() {
        return R.array.search_target_gender;
    }


}
