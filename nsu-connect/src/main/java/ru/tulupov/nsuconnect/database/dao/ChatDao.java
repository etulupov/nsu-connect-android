package ru.tulupov.nsuconnect.database.dao;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.tulupov.nsuconnect.database.DatabaseContract;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.Message;

public class ChatDao extends BaseDaoImpl<Chat, Integer> {
    public ChatDao(ConnectionSource connectionSource,
                   Class<Chat> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Chat> getAllRoles() throws SQLException {
        return this.queryForAll();
    }

    public Chat getLast() throws SQLException {
        List<Chat> list = query(queryBuilder()
                .orderBy(DatabaseContract.Chat.ID, false).limit(1).prepare());
        return list.isEmpty() ? null : list.get(0);
    }


    public Chat getChat(int id) throws SQLException {

        QueryBuilder<Chat, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq(DatabaseContract.Chat.ID, id);
        List<Chat> list = query(queryBuilder.prepare());
        return list.isEmpty() ? null : list.get(0);
    }
}
