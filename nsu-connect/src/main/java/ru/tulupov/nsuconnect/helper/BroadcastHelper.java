package ru.tulupov.nsuconnect.helper;


import android.content.Intent;

public class BroadcastHelper {
    private static final String AUTHORITY = "ru.tulupov.nsuconnect";

    public static final String ACTION_UPDATE_TYPING_STATUS = AUTHORITY + "/" + "typing";
    public static final String ACTION_USER_DISCONNECTED = AUTHORITY + "/" + "disconnect";
    public static final String EXTRA_IS_TYPING = "is_typing";
    public static final String EXTRA_CHAT_ID = "chat_id";

    public static Intent getChatDisconnectIntent(int chatId) {
        return new Intent(ACTION_USER_DISCONNECTED)
                .putExtra(EXTRA_CHAT_ID, chatId);
    }

    public static Intent getChatTypingIntent(int chatId, boolean typingStatus) {
        return new Intent(ACTION_UPDATE_TYPING_STATUS)
                .putExtra(EXTRA_CHAT_ID, chatId)
                .putExtra(EXTRA_IS_TYPING, typingStatus);
    }
}
