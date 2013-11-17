package ru.tulupov.nsuconnect.model;


import com.google.gson.annotations.SerializedName;

public class Online {
    @SerializedName("online")
    private int count;

    @SerializedName("men")
    private int menCount;

    @SerializedName("women")
    private int womenCount;

    @SerializedName("unknown")
    private int unknownCount;


    public int getCount() {
        return count;
    }

    public int getMenCount() {
        return menCount;
    }

    public int getWomenCount() {
        return womenCount;
    }

    public int getUnknownCount() {
        return unknownCount;
    }
}
