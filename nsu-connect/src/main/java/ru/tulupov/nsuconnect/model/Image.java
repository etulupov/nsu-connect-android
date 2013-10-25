package ru.tulupov.nsuconnect.model;


import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;


import static ru.tulupov.nsuconnect.database.DatabaseContract.Image.FILE;
import static ru.tulupov.nsuconnect.database.DatabaseContract.Image.ID;


public class Image {


    @DatabaseField(generatedId = true, columnName = ID)
    private int id;


    @DatabaseField(columnName = FILE)
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
