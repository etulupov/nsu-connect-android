package ru.tulupov.nsuconnect.model;


import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import static ru.tulupov.nsuconnect.database.DatabaseContract.Message.*;

public class Message {
    @SerializedName("msg")
    @DatabaseField(columnName = TEXT)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}
