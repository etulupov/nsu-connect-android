package ru.tulupov.nsuconnect.helper;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

import ru.tulupov.nsuconnect.activity.HomeActivity;
import ru.tulupov.nsuconnect.activity.WelcomeActivity;

public class IntentActionHelper {
    public static final Intent getWeclomeIntent(Context context) {
        return new Intent(context, WelcomeActivity.class);
    }

    public static final Intent getHomeIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    public static final Intent getHomeNewChatIntent(Context context) {
        return new Intent(context, HomeActivity.class).putExtra(HomeActivity.EXTRA_NEW_CHAT, true);
    }


    public static Intent getSelectFromGallery1Intent(final Context context) {
        return new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }


    public static Intent getCameraIntent(final Context context, File file) {
        return new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
    }
}
