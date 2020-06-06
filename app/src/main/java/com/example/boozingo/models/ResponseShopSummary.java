package com.example.boozingo.models;

import com.example.boozingo.util.Summary;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseShopSummary {
    @SerializedName("message")
    String message;
    @SerializedName("data")
    ListShopSummary data;

    public String getMessage() {
        return message;
    }

    public ListShopSummary getData() {
        return data;
    }

    public class ListShopSummary{
        @SerializedName("list")
        ArrayList<Summary> listShopSummaries;

        public ArrayList<Summary> getListRestaurantSummaries() {
            return listShopSummaries;
        }
    }
}
