package ru.tulupov.nsuconnect.helper;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.tulupov.nsuconnect.R;
import ru.tulupov.nsuconnect.activity.HomeActivity;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Message;
import ru.tulupov.nsuconnect.util.ApplicationUtils;

public class NotificationHelper {
    private static final String TAG = NotificationHelper.class.getSimpleName();
    private static final int NOTIFICATION_ID = 2;

    public static void notify(Context context, Message message, Chat chat) {

        if (!ApplicationUtils.isScreenOn(context) || ApplicationUtils.isApplicationSentToBackground(context)) {
            int unreadCount = 0;
            try {


                unreadCount = HelperFactory.getHelper().getMessageDao().getUnreadCount(chat.getId());

            } catch (SQLException e) {
                Log.e(TAG, "error", e);
            }


            Intent resultIntent = new Intent(context, HomeActivity.class);


            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentTitle(chat.getName())
                    .setContentText(message.getMessage())
                    .setWhen(System.currentTimeMillis())


                    .setLights(Color.GREEN, 250, 500)
//                    .setVibrate(new long[]{100, 200, 100, 200})
                    .setAutoCancel(false)
                    .setSmallIcon(R.drawable.ic_messages)
                    .setContentIntent(resultPendingIntent);

            if (unreadCount != 0) {
                builder.setNumber(unreadCount);
            }


        }


    }

    public static void hideNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }


}
