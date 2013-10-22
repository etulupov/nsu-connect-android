package ru.tulupov.nsuconnect.model;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;


import static ru.tulupov.nsuconnect.database.DatabaseContract.User.*;


public class User {
    public static final int TYPE_SYSTEM = 0;
    public static final int TYPE_YOUR = 1;
    public static final int TYPE_OTHER = 2;


    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = TYPE)

    private int type;


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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
