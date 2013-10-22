package ru.tulupov.nsuconnect.model;


import com.google.gson.annotations.SerializedName;

public class Online {
    @SerializedName("online")
    private int count;

    public int getCount() {
        return count;
    }
}
