package com.example.boozingo.util;

import com.google.gson.annotations.SerializedName;

public class Summary {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("avg_rating")
    String avgRating;
    @SerializedName("distance")
    String distance;
    @SerializedName("type")
    String type;
    @SerializedName("details")
    String details;
    @SerializedName("cost")
    String cost;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public String getDistance() {
        return distance;
    }

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public String getCost() {
        return cost;
    }
}
