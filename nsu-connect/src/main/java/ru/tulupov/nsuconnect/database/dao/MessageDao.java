package ru.tulupov.nsuconnect.database.dao;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.tulupov.nsuconnect.database.DatabaseConstants;
import ru.tulupov.nsuconnect.database.DatabaseContract;
import ru.tulupov.nsuconnect.database.HelperFactory;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Message;

public class MessageDao extends BaseDaoImpl<Message, Integer> {
    public MessageDao(ConnectionSource connectionSource,
                      Class<Message> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Message> getAllRoles() throws SQLException {
        List<Message> list = queryForAll();
        for (Message m : list) {
            refresh(m);
        }
        return list;
    }


    public List<Message> getUnsentMessagesByChat(Chat chat) throws SQLException {
        QueryBuilder<Message, Integer> queryBuilder = HelperFactory.getHelper().getMessageDao().queryBuilder();
        queryBuilder.where().eq(DatabaseContract.Message.CHAT, chat.getId()).and()
                .eq(DatabaseContract.Message.SEND_FLAG, DatabaseConstants.BOOLEAN_FALSE);
        queryBuilder.orderBy(DatabaseContract.Message.DATE, true);
        PreparedQuery<Message> preparedQuery = queryBuilder.prepare();
        List<Message> messages = HelperFactory.getHelper().getMessageDao().query(preparedQuery);

        return messages;

    }

    public void updateReadFlag(Chat chat) throws SQLException {
        UpdateBuilder<Message, Integer> updateBuilder = updateBuilder();

        updateBuilder.where().eq(DatabaseContract.Message.CHAT, chat.getId()).and()
                .eq(DatabaseContract.Message.READ_FLAG, DatabaseConstants.BOOLEAN_FALSE);
        updateBuilder.updateColumnValue(DatabaseContract.Message.READ_FLAG, DatabaseConstants.BOOLEAN_TRUE);

        update(updateBuilder.prepare());
    }

    public List<Message> getUnreadMessagesByChat(Chat chat) throws SQLException {
        QueryBuilder<Message, Integer> queryBuilder = HelperFactory.getHelper().getMessageDao().queryBuilder();
        queryBuilder.where().eq(DatabaseContract.Message.CHAT, chat.getId()).and()
                .eq(DatabaseContract.Message.READ_FLAG, DatabaseConstants.BOOLEAN_FALSE);
        queryBuilder.orderBy(DatabaseContract.Message.DATE, true);
        PreparedQuery<Message> preparedQuery = queryBuilder.prepare();
        List<Message> messages = HelperFactory.getHelper().getMessageDao().query(preparedQuery);

        return messages;

    }


    public Message getLastMessageByChat(Chat chat) throws SQLException {
        QueryBuilder<Message, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq(DatabaseContract.Message.CHAT, chat.getId());
        queryBuilder.orderBy(DatabaseContract.Message.ID, false);
        queryBuilder.limit(1);

        List<Message> list = query(queryBuilder.prepare());

        return list.isEmpty() ? null : list.get(0);

    }

    public Message get(int id) throws SQLException {
        QueryBuilder<Message, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq(DatabaseContract.Message.ID, id);


        List<Message> list = query(queryBuilder.prepare());

        return list.isEmpty() ? null : list.get(0);

    }


    public int getUnreadCount(int id) throws SQLException {

        QueryBuilder<Message, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq(DatabaseContract.Message.CHAT, id)
                .and().eq(DatabaseContract.Message.READ_FLAG, DatabaseConstants.BOOLEAN_FALSE);


        List<Message> list = query(queryBuilder.prepare());

        return list.size();

    }
}
