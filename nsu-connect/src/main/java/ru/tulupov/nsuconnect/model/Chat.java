package ru.tulupov.nsuconnect.model;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import ru.tulupov.nsuconnect.database.DatabaseContract;

import static ru.tulupov.nsuconnect.database.DatabaseContract.Chat.*;

public class Chat {
    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(columnName = NAME)
    private String name;

    @DatabaseField(columnName = DATE, dataType = DataType.JAVA_DATE_LONG)
    private Date date;

    @DatabaseField(columnName = ACTIVE_FLAG)
    private boolean active;

    @DatabaseField(columnName = LAST_MESSAGE, canBeNull = true)
    private Integer lastMessageId;

    private Message lastMessage;

    public Integer getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(Integer lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
