package ru.tulupov.nsuconnect;

import android.app.Application;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.ExceptionReporter;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Logger;
import com.google.analytics.tracking.android.Tracker;

import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.helper.SoundHelper;


public class NsuConnectApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Thread.UncaughtExceptionHandler handler = new ExceptionReporter(
                EasyTracker.getInstance(this),
                GAServiceManager.getInstance(),
                Thread.getDefaultUncaughtExceptionHandler(),
                this);

        Thread.setDefaultUncaughtExceptionHandler(handler);

        if (BuildConfig.DEBUG) {
            GoogleAnalytics.getInstance(this)
                    .getLogger()
                    .setLogLevel(Logger.LogLevel.VERBOSE);
        }

        SoundHelper.init(this);

        HelperFactory.setHelper(getApplicationContext());


    }
}
