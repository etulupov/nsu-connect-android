package ru.tulupov.nsuconnect.helper;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Settings;

public class SettingsHelper {
    private static final String PREFERENCES_NAME = "preferences";
    private static final String SETTINGS = "settings";
    private static final String IS_FIRST_RUN = "is_first_run_v3";


    public SettingsHelper() {
        throw new UnsupportedOperationException();
    }

    public static Settings getSettings(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Settings settings = gson.fromJson(preferences.getString(SETTINGS, ""), Settings.class);
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    public static void setSettings(Context context, Settings settings) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        preferences.edit().putString(SETTINGS, gson.toJson(settings)).commit();
    }

    public static void setChat(Context context, Chat chat) {
        Settings settings = getSettings(context);
        settings.setChat(chat);
        setSettings(context, settings);
    }

    public static Chat getChat(Context context) {
        Settings settings = getSettings(context);
        return settings.getChat();
    }

    public static boolean isFirstRun(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(IS_FIRST_RUN, true);
    }

    public static void setFirstRun(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(IS_FIRST_RUN, false).commit();
    }

}
