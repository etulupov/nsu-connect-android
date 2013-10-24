package ru.tulupov.nsuconnect.database.loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import ru.tulupov.nsuconnect.database.DatabaseContract;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Message;


public class ChatLoader extends AsyncTaskLoader<List<Chat>> {
    private static final String TAG = ChatLoader.class.getSimpleName();


    public ChatLoader(Context context) {
        super(context);
    }

    @Override
    public List<Chat> loadInBackground() {
        try {
            QueryBuilder<Chat, Integer> queryBuilder = HelperFactory.getHelper().getChatDao().queryBuilder();
            queryBuilder.orderBy(DatabaseContract.Chat.DATE, false);
            PreparedQuery<Chat> preparedQuery = queryBuilder.prepare();
            List<Chat> chats = HelperFactory.getHelper().getChatDao().query(preparedQuery);

            return chats;
        } catch (SQLException e) {
            Log.e(TAG, "Error", e);
        }
        return null;
    }


}
