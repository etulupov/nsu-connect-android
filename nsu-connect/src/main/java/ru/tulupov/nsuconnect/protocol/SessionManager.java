package ru.tulupov.nsuconnect.protocol;


import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.model.Chat;

public class SessionManager {
    private static final String TAG = SessionManager.class.getSimpleName();
    private Map<Integer, Session> sessions;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sessions = new HashMap<Integer, Session>();
    }

    public Session init(int id) {

        try {
            Chat chat = HelperFactory.getHelper().getChatDao().getChat(id);


            Session session = new Session(context, chat);

            sessions.put(id, session);
            session.onCreate();
            return session;
        } catch (SQLException e) {
            Log.e(TAG, "cannot create chat entity", e);
        }
        return null;
    }

    public Session get(int id) {
        return sessions.get(id);
    }

    public void onCreate() {

    }

    public void onDestroy() {
        for (Session session : sessions.values()) {
            session.onDestroy();
        }
    }
}
