package ru.tulupov.nsuconnect.model;


import com.google.gson.annotations.SerializedName;

public class Success {
    @SerializedName("success")
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Success{" +
                "code=" + code +
                '}';
    }
}
