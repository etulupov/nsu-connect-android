package ru.tulupov.nsuconnect.model;


import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.io.File;
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

    @SerializedName("url")
    @DatabaseField(columnName = IMAGE, canBeNull = true)
    private String url;

    @DatabaseField(columnName = DATE, dataType = DataType.JAVA_DATE_LONG)
    private transient Date date;

    @DatabaseField(columnName = CHAT, foreign = true)
    private transient Chat chat;

    @DatabaseField(columnName = USER, foreign = true)
    private transient User user;

    @DatabaseField(columnName = READ_FLAG, dataType = DataType.BOOLEAN)
    private transient boolean readFlag;

    @DatabaseField(columnName = SEND_FLAG, dataType = DataType.BOOLEAN)
    private transient boolean sentFlag;

    //    @DatabaseField(columnName = IMAGE, foreign = true)
    private transient Image image;


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

    public Chat getChat() {
        return chat;
    }

    public boolean isSentFlag() {
        return sentFlag;
    }

    public void setSentFlag(boolean sentFlag) {
        this.sentFlag = sentFlag;
    }


    public boolean isReadFlag() {
        return readFlag;
    }

    public void setReadFlag(boolean readFlag) {
        this.readFlag = readFlag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
