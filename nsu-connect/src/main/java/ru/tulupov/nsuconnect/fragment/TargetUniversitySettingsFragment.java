package ru.tulupov.nsuconnect.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import ru.tulupov.nsuconnect.R;


public class TargetUniversitySettingsFragment extends BaseUniversitySettingsFragment {


    public static TargetUniversitySettingsFragment newInstance(final Context context) {
        return (TargetUniversitySettingsFragment) Fragment.instantiate(context, TargetUniversitySettingsFragment.class.getName());
    }


    @Override
    protected int getTitleTextId() {
        return R.string.search_enter_target_university;
    }

    @Override
    protected int getListChoiceMode() {
        return ListView.CHOICE_MODE_MULTIPLE;
    }


}
