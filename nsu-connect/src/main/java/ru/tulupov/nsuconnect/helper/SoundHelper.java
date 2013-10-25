package ru.tulupov.nsuconnect.helper;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

public class SoundHelper {
    private static SoundPool SOUND_POOL;
    private static int ID_MESSAGE ;

    public static void init(Context context) {
        int MAX_STREAMS =1;
        SOUND_POOL = new SoundPool(MAX_STREAMS, AudioManager.STREAM_NOTIFICATION, 0);
        try {
            ID_MESSAGE = SOUND_POOL.load(context.getAssets().openFd("message_sound.wav"), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void beep() {
        SOUND_POOL.play(ID_MESSAGE, 1, 1, 0, 0, 1);
    }
}
