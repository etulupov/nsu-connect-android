package ru.tulupov.nsuconnect.activity;

import android.app.Activity;

import com.google.analytics.tracking.android.EasyTracker;


public class BaseActivity extends Activity {
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
}
