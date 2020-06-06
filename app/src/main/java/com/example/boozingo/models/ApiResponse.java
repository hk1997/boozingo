package com.example.boozingo.models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("message")
    String message;

    @SerializedName("data")
    JsonObject data;

    public String getMessage() {
        return message;
    }

    public JsonObject getData() {
        return data;
    }
}
