package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import ru.tulupov.nsuconnect.R;


public class TargetAgeSettingsFragment extends BaseSearchSettingsFragment {
    public static TargetAgeSettingsFragment newInstance(final Context context) {

        return (TargetAgeSettingsFragment) Fragment.instantiate(context, TargetAgeSettingsFragment.class.getName());
    }


    @Override
    protected int getTitleTextId() {
        return R.string.search_enter_target_age;
    }

    @Override
    protected int getItemsArrayId() {
        return R.array.search_target_age;
    }


}
