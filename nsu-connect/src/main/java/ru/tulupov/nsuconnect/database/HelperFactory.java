package ru.tulupov.nsuconnect.database;


import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

public class HelperFactory {
    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getHelper() {
        return databaseHelper;
    }

    public static void setHelper(Context context) {
        OpenHelperManager.setOpenHelperFactory(new OpenHelperManager.SqliteOpenHelperFactory() {
            @Override
            public OrmLiteSqliteOpenHelper getHelper(Context context) {
                return new DatabaseHelper(context);
            }
        });
        databaseHelper = (DatabaseHelper) OpenHelperManager.getHelper(context);
    }

    public static void releaseHelper() {
        OpenHelperManager.release();
        databaseHelper = null;
    }
}
