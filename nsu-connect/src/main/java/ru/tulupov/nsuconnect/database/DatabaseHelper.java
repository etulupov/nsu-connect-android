package ru.tulupov.nsuconnect.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ru.tulupov.nsuconnect.database.dao.ChatDao;
import ru.tulupov.nsuconnect.database.dao.MessageDao;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Message;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "database.db";


    private static final int DATABASE_VERSION = 1;


    private MessageDao messageDao = null;
    private ChatDao chatDao = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Выполняется, когда файл с БД не найден на устройстве
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Message.class);
            TableUtils.createTable(connectionSource, Chat.class);

        } catch (SQLException e) {
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer) {
        try {

            TableUtils.dropTable(connectionSource, Message.class, true);
            TableUtils.dropTable(connectionSource, Chat.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "error upgrading db " + DATABASE_NAME + "from ver " + oldVer);
            throw new RuntimeException(e);
        }
    }


    public MessageDao getMessageDao() throws SQLException {
        if (messageDao == null) {
            messageDao = new MessageDao(getConnectionSource(), Message.class);
        }
        return messageDao;
    }

    public ChatDao getChatDao() throws SQLException {
        if (chatDao == null) {
            chatDao = new ChatDao(getConnectionSource(), Chat.class);
        }
        return chatDao;
    }

    @Override
    public void close() {
        super.close();
        messageDao = null;
        chatDao = null;

    }
}
