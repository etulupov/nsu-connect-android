package ru.tulupov.nsuconnect.model;


import com.google.gson.annotations.SerializedName;

public class Uid {
    @SerializedName("uid")
    private String uid;

    public String getUid() {
        return uid;
    }
}
