package ru.tulupov.nsuconnect.database;


import android.content.Context;
import android.net.Uri;

public class ContentUriHelper {
    public static Uri getChatUri() {
        return DatabaseConstants.URI_CHAT;
    }

    public static Uri getConversationUri(int chatId) {
        return DatabaseConstants.URI_CONVERSATION.buildUpon().appendPath(String.valueOf(chatId)).build();
    }



    public static void notifyChange(Context context, Uri uri) {
        context.getContentResolver().notifyChange(uri, null);
    }
}
