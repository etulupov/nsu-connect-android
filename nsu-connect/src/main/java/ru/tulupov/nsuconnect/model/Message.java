package ru.tulupov.nsuconnect.model;


import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import static ru.tulupov.nsuconnect.database.DatabaseContract.Message.*;

public class Message {

    public enum Type {
        SYSTEM, OWN, OTHER
    }

    @DatabaseField(generatedId = true, columnName = ID)
    private transient int id;

    @SerializedName("msg")
    @DatabaseField(columnName = TEXT)
    private String message;

    @DatabaseField(columnName = DATE, dataType = DataType.JAVA_DATE)
    private transient Date date;

    @DatabaseField(columnName = CHAT, foreign = true)
    private transient Chat chat;

    @DatabaseField(columnName = USER, foreign = true)
    private transient User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
