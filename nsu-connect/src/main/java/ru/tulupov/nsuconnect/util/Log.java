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

        EasyTracker.getInstance(CONTEXT).send(MapBuilder.createException(String.format("%s: %s", tag, msg), false).build());
        BugSenseHandler.addCrashExtraData(tag, msg);
    }

    public static void e(String tag, String msg, Exception tr) {
        android.util.Log.e(tag, msg, tr);

        EasyTracker.getInstance(CONTEXT).send(MapBuilder.createException(String.format("%s: %s: %s",
                tag,
                msg,
                android.util.Log.getStackTraceString(tr)),
                false).build());


        BugSenseHandler.sendExceptionMessage(tag, msg, tr);
    }

    public static void v(String tag, String msg) {
        android.util.Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        android.util.Log.d(tag, msg);
    }
}
