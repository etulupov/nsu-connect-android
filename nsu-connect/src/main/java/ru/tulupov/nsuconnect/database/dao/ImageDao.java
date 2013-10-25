package ru.tulupov.nsuconnect.database.dao;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.tulupov.nsuconnect.model.Image;
import ru.tulupov.nsuconnect.model.User;

public class ImageDao extends BaseDaoImpl<Image, Integer> {
    public ImageDao(ConnectionSource connectionSource,
                    Class<Image> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }




}
