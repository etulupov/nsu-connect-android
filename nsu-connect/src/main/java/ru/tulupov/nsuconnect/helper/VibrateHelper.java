package ru.tulupov.nsuconnect.helper;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;

import java.io.IOException;

public class VibrateHelper {
    private static Vibrator VIBRATOR;
    private static long[] PATTERN = {50, 100, 75, 100};

    public static void init(Context context) {
        VIBRATOR = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static void vibrate() {
        if (VIBRATOR != null) {
            VIBRATOR.vibrate(PATTERN, -1);
        }
    }
}
