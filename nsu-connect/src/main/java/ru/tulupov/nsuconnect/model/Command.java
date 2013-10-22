package ru.tulupov.nsuconnect.model;


import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Command {
    @SerializedName("ids")
    private Map<String, String> ids;
    @SerializedName("data")
    private Status status;

    public Map<String, String> getIds() {
        return ids;
    }

    public Status getStatus() {
        return status;
    }
}
