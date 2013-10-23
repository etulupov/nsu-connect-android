package ru.tulupov.nsuconnect.helper;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

public class SoundHelper {
    public static void beep(Context context) {
        int MAX_STREAMS =1;
        SoundPool sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

            }
        });

       int soundIdShot=0;


        try {
            soundIdShot = sp.load(context.getAssets().openFd("message_sound.wav"), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sp.play(soundIdShot, 1, 1, 0, 0, 1);
    }
}
