package ru.tulupov.nsuconnect.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.fragment.BaseFragment;
import ru.tulupov.nsuconnect.fragment.ConversationsFragment;
import ru.tulupov.nsuconnect.fragment.WelcomeFragment;
import ru.tulupov.nsuconnect.slidingmenu.SlidingMenuFragment;
import ru.tulupov.nsuconnect.slidingmenu.SlidingMenyActivity;


public class BaseSlidingMenuActivity extends SlidingMenyActivity implements BaseActivityInterface {


    private BaseFragment content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlidingMenuFragment slidingMenuFragment = new SlidingMenuFragment();
        setBehindContentView(R.layout.menu_frame);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_frame, slidingMenuFragment)
                .commit();


        slidingMenuFragment.setOnItemClickListener(new SlidingMenuFragment.OnItemClickListener() {
            @Override
            public void onClick(int id) {
                onMenuItemClick(id);
            }
        });

        showFragment(WelcomeFragment.newInstance(getApplicationContext()));
    }

    protected void onMenuItemClick(int id) {
        switch (id) {
            case R.id.menu_home:
                showFragment(WelcomeFragment.newInstance(getApplicationContext()));
                return;
            case R.id.menu_converstions:
                showFragment(ConversationsFragment.newInstance(getApplicationContext()));
                return;
        }
    }

    @Override
    public void addFragment(BaseFragment fragment) {
        String tag = ((Object) fragment).getClass().getSimpleName();
        setTitle(fragment.getTitleId());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment, tag)
                .addToBackStack(tag)
                .commit();
        getSlidingMenu().showContent();
    }

    @Override
    public void showFragment(BaseFragment fragment) {
        String tag = ((Object) fragment).getClass().getSimpleName();
        setTitle(fragment.getTitleId());
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment, tag)
                .addToBackStack(tag)
                .commit();

        getSlidingMenu().showContent();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            int last = getSupportFragmentManager().getBackStackEntryCount() - 1;

            FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(last);
            BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(entry.getName());
            if (fragment != null) {
                if (fragment.onBackPressed()) {
                    return;
                }
            }
        }

        super.onBackPressed();
    }
}
