package ru.tulupov.nsuconnect.model;


import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import static ru.tulupov.nsuconnect.database.DatabaseContract.Message.*;

public class Message {

    @DatabaseField(generatedId = true, columnName = ID)
    private transient int id;

    @SerializedName("msg")
    @DatabaseField(columnName = TEXT)
    private String message;

    @DatabaseField(columnName = DATE, dataType = DataType.JAVA_DATE)
    private transient Date date;

    @DatabaseField(columnName = CHAT, foreign = true)
    private transient Chat chat;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
