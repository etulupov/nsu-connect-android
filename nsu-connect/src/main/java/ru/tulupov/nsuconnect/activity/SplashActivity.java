package ru.tulupov.nsuconnect.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.analytics.tracking.android.EasyTracker;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.helper.IntentActionHelper;


public class SplashActivity extends Activity {
    private static final long DELAY = 1500;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startActivity(IntentActionHelper.getHomeIntent(getApplicationContext()));
            finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_in);
        findViewById(R.id.logo).startAnimation(animation);

        handler.postDelayed(runnable, DELAY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EasyTracker.getInstance(this).activityStart(this);
    }
}