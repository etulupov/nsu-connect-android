package ru.tulupov.nsuconnect.helper;


import android.content.Context;
import android.content.Intent;

import ru.tulupov.nsuconnect.activity.MainActivity;

public class IntentActionHelper {

    public static final Intent getHomeIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}
