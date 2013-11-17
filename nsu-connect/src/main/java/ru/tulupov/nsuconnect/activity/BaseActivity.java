package ru.tulupov.nsuconnect.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.google.analytics.tracking.android.EasyTracker;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.fragment.BaseFragment;
import ru.tulupov.nsuconnect.util.FragmentUtils;


public class BaseActivity extends ActionBarActivity implements BaseActivityInterface {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_frame);
    }

    @Override
    public void onResume() {
        super.onResume();

        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    public void addFragment(BaseFragment fragment) {
        String tag = ((Object) fragment).getClass().getSimpleName();
        if (fragment.getTitleId() != 0) {
            getSupportActionBar().setTitle(fragment.getTitleId());
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void showFragment(BaseFragment fragment) {
        FragmentUtils.clearBackStack(getSupportFragmentManager());
        addFragment(fragment);

    }

    @Override
    public void showDialog(DialogFragment fragment) {

    }


    @Override
    public SlidingMenu getSlidingMenu() {
        return null;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onBackPressed() {


        FragmentManager fragmentManager = getSupportFragmentManager();

        BaseFragment topFragment = getTopFragment();
        if (topFragment != null) {
            if (topFragment.onBackPressed()) {
                return;
            }
        }


        closeFragment();


    }

    private BaseFragment getTopFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() != 0) {
            int last = fragmentManager.getBackStackEntryCount() - 1;

            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(last);
            return (BaseFragment) fragmentManager.findFragmentByTag(entry.getName());
        }
        return null;
    }

    @Override
    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() != 0) {

            fragmentManager.popBackStackImmediate();
            if (fragmentManager.getBackStackEntryCount() == 0) {
                finish();
                return;
            }

            BaseFragment topFragment = getTopFragment();
            setTitle(topFragment.getTitleId());


        }
    }
}
