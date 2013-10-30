package ru.tulupov.nsuconnect.util;


import android.content.Context;

import com.bugsense.trace.BugSenseHandler;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class Log {
    private static Context CONTEXT;

    public static void init(Context context) {
        CONTEXT = context.getApplicationContext();
    }


    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);

        try {
            EasyTracker.getInstance(CONTEXT).send(MapBuilder.createException(String.format("%s: %s", tag, msg), false).build());
        } catch (Throwable e) {

        }
        try {
            BugSenseHandler.addCrashExtraData(tag, msg);
        } catch (Throwable e) {

        }
    }

    public static void e(String tag, String msg, Exception tr) {
        android.util.Log.e(tag, msg, tr);
        try {
            EasyTracker.getInstance(CONTEXT).send(MapBuilder.createException(String.format("%s: %s: %s",
                    tag,
                    msg,
                    android.util.Log.getStackTraceString(tr)),
                    false).build());
        } catch (Throwable e) {

        }
        try {
            BugSenseHandler.sendExceptionMessage(tag, msg, tr);
        } catch (Throwable e) {

        }
    }

    public static void v(String tag, String msg) {
        android.util.Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        android.util.Log.d(tag, msg);
    }
}
