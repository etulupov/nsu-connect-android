package ru.tulupov.nsuconnect.util;


import android.support.v4.app.FragmentManager;

public class FragmentUtils {
    public static void clearBackStack(FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }
}
