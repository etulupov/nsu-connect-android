package ru.tulupov.nsuconnect;

import android.app.Application;

import ru.tulupov.nsuconnect.database.HelperFactory;


public class NsuConnectApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }
}
