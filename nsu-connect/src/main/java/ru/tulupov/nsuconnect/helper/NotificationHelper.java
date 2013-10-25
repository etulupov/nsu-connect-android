package ru.tulupov.nsuconnect.helper;


import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Message;

public class NotificationHelper {
    private static final String TAG = NotificationHelper.class.getSimpleName();

    public static void notify(Context context, Message message, Chat chat) {
        try {
            List<Message> messages = HelperFactory.getHelper().getMessageDao().getUnreadMessagesByChat(chat);
            Log.e("xxx", "unread mess=" + messages);
        } catch (SQLException e) {
            Log.e(TAG, "error", e);
        }
    }

}
