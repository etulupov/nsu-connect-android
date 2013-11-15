package ru.tulupov.nsuconnect.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.rampo.updatechecker.UpdateChecker;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.fragment.AboutFragment;
import ru.tulupov.nsuconnect.fragment.BaseFragment;
import ru.tulupov.nsuconnect.fragment.MessagesFragment;
import ru.tulupov.nsuconnect.fragment.SettingsFragment;
import ru.tulupov.nsuconnect.fragment.WelcomeFragment;
import ru.tulupov.nsuconnect.helper.ChatHelper;
import ru.tulupov.nsuconnect.slidingmenu.SlidingMenuFragment;


public class BaseSlidingMenuActivity extends SlidingFragmentActivity implements BaseActivityInterface {


    private int currentItemId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_frame);
        setBehindContentView(R.layout.menu_frame);

        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_left_menu);

        SlidingMenuFragment slidingMenuFragment = new SlidingMenuFragment();
        setBehindContentView(R.layout.menu_frame);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_frame, slidingMenuFragment)
                .commit();


        slidingMenuFragment.setOnItemClickListener(new SlidingMenuFragment.OnItemClickListener() {
            @Override
            public void onClick(int id) {
                if (currentItemId != id) {
                    currentItemId = id;
                    onMenuItemClick(id);
                } else {
                    showContent();
                }
            }
        });

        slidingMenuFragment.setMenuItems(R.menu.main);


    }

    @Override
    public void setTitle(int titleId) {
        if (titleId != 0) {
            super.setTitle(titleId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        BaseFragment topFragment = getTopFragment();
        if (topFragment != null) {
            if (topFragment.onOptionsItemSelected(item)) {
                return true;
            }
        }


        switch (item.getItemId()) {
            case android.R.id.home:
                toggle();
                return true;

        }

        return false;
    }

    protected void onMenuItemClick(int id) {
        switch (id) {
            case R.id.menu_start_chat:
                ChatHelper.openChatFragment(this);
                EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("UX", "menu_left", "menu_start_chat", null).build());

                return;
            case R.id.menu_messages:
                showFragment(MessagesFragment.newInstance(getApplicationContext()));
                EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("UX", "menu_left", "menu_messages", null).build());
                return;
            case R.id.menu_settings:
                addFragment(SettingsFragment.newInstance(getApplicationContext()));
                EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("UX", "menu_left", "menu_settings", null).build());
                return;

            case R.id.menu_about_application:
                addFragment(AboutFragment.newInstance(getApplicationContext()));
                EasyTracker.getInstance(getActivity()).send(MapBuilder.createEvent("UX", "menu_left", "menu_about_application", null).build());
                return;
        }
    }


    private void setCurrentItemId(int currentItemId) {
        this.currentItemId = currentItemId;
    }

    @Override
    public void addFragment(BaseFragment fragment) {
        String tag = ((Object) fragment).getClass().getSimpleName();
        setTitle(fragment.getTitleId());
        setCurrentItemId(fragment.getMenuItemId());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment, tag)
                .addToBackStack(tag)
                .commit();
        getSlidingMenu().showContent();
    }

    @Override
    public void showFragment(BaseFragment fragment) {
        clearBackStack();
        addFragment(fragment);
    }

    @Override
    public void showDialog(DialogFragment fragment) {
        fragment.show(getSupportFragmentManager(), null);
    }

    private void clearBackStack() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }
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
    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            getSlidingMenu().toggle();
//            return true;
//        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        if (getSlidingMenu().isMenuShowing()) {
            getSlidingMenu().toggle();
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        BaseFragment topFragment = getTopFragment();
        if (topFragment != null) {
            if (topFragment.onBackPressed()) {
                return;
            }
        }


        closeFragment();


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (getSlidingMenu().isMenuShowing()) toggle();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            UpdateChecker.checkForNotification(this);
        } catch (Exception e) {

        }
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
            setCurrentItemId(topFragment.getMenuItemId());


        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
