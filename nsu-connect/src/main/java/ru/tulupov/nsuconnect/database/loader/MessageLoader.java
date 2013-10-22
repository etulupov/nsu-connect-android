package ru.tulupov.nsuconnect.database.loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.sql.SQLException;
import java.util.List;

import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Message;


public class MessageLoader extends AsyncTaskLoader<List<Message>> {
    private static final String TAG = MessageLoader.class.getSimpleName();

    public MessageLoader(Context context, Chat chat) {
        super(context);
    }

    @Override
    public List<Message> loadInBackground() {
        try {
            return HelperFactory.getHelper().getMessageDao().getAllRoles();
        } catch (SQLException e) {
            Log.e(TAG, "Error", e);
        }
        return null;
    }


}
