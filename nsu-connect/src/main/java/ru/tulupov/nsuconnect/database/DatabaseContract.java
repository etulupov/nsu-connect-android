package ru.tulupov.nsuconnect.database;

import android.net.Uri;

public interface DatabaseContract {
    interface Message {
        String ID = "id";
        String TEXT = "text";
        String DATE = "date";
        String CHAT = "chat";
        String USER = "user";
        String SEND_FLAG = "send_flag";
        String READ_FLAG = "send_flag";

    }

    interface Chat {
        String ID = "id";
        String NAME = "name";
        String DATE = "date";

    }

    interface User {
        String ID = "id";
        String NAME = "name";
        String TYPE = "type";

    }
}
