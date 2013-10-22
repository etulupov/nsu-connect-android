package ru.tulupov.nsuconnect.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class PagerAdapter extends FragmentPagerAdapter {
    private List<? extends Fragment> fragments;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void setFragments(List<? extends Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }
}
