package ru.tulupov.nsuconnect.database.dao;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.tulupov.nsuconnect.database.DatabaseContract;
import ru.tulupov.nsuconnect.model.Chat;
import ru.tulupov.nsuconnect.model.User;

public class UserDao extends BaseDaoImpl<User, Integer> {
    public UserDao(ConnectionSource connectionSource,
                   Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }




}
