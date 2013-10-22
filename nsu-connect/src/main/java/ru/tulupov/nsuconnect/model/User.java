package ru.tulupov.nsuconnect.model;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;


import static ru.tulupov.nsuconnect.database.DatabaseContract.User.ID;
import static ru.tulupov.nsuconnect.database.DatabaseContract.User.NAME;

public class User {
    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(columnName = NAME)
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
