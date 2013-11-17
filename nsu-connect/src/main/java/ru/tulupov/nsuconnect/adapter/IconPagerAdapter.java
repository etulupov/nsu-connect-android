package ru.tulupov.nsuconnect.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class IconPagerAdapter extends PagerAdapter implements com.viewpagerindicator.IconPagerAdapter {
    private List<Integer> icons;

    public IconPagerAdapter(FragmentManager fm, List<? extends Fragment> fragments, List<Integer> icons) {
        super(fm, fragments);

        this.icons = icons;
    }

    @Override
    public int getIconResId(int index) {
        return icons.get(index);
    }
}
