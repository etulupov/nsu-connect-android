package ru.tulupov.nsuconnect.database.dao;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

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
}
