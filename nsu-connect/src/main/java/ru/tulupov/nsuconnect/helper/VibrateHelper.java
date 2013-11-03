package ru.tulupov.nsuconnect.helper;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;

import java.io.IOException;

public class VibrateHelper {
    private static Vibrator VIBRATOR;
    private static long[] PATTERN = {50, 150, 100, 150};
    private static AudioManager AUDIO_MANAGER;

    public static void init(Context context) {
        VIBRATOR = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        AUDIO_MANAGER = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public static void vibrate() {
        if (VIBRATOR != null && AUDIO_MANAGER != null) {
            if (AUDIO_MANAGER.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
                VIBRATOR.vibrate(PATTERN, -1);
            }
        }
    }
}
