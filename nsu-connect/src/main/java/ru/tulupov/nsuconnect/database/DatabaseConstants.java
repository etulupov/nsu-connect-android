package ru.tulupov.nsuconnect.database;

import android.net.Uri;

public interface DatabaseConstants {
    String AUTHORITY = "ru.tulupov.nsuconnect";
    String ACTION_UPDATE_MESSAGE_LIST = AUTHORITY + "/" + "message";
    String ACTION_UPDATE_TYPING_STATUS = AUTHORITY + "/" + "typing";
    String EXTRA_IS_TYPING = "is_typing";

    String BOOLEAN_FALSE = "0";
    String BOOLEAN_TRUE = "1";


    Uri URI_BASE = Uri.parse("content://ru.tulupov.nsuconnect");
    Uri URI_CHAT = URI_BASE.buildUpon().appendPath("chat").build();
    Uri URI_CONVERSATION = URI_BASE.buildUpon().appendPath("converstaion").build();
    Uri URI_COUNTER = URI_BASE.buildUpon().appendPath("counter").build();

}
