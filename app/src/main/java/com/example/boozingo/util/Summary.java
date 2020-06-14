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
    @SerializedName("address")
    String address;
    @SerializedName("city_id")
    String cityId;
    @SerializedName("longitude")
    String longitude;
    @SerializedName("latitude")
    String latitude;
    @SerializedName("time")
    String time;
    @SerializedName("contact")
    String contact;
    @SerializedName("not_working_day")
    String notWorkingDay;
    @SerializedName("booze_served")
    String boozeServed;
    @SerializedName("food")
    String food;
    @SerializedName("rated_by")
    String ratedBy;

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

    public String getAddress() {
        return address;
    }

    public String getCityId() {
        return cityId;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getTime() {
        return time;
    }

    public String getContact() {
        return contact;
    }

    public String getNotWorkingDay() {
        return notWorkingDay;
    }

    public String getBoozeServed() {
        return boozeServed;
    }

    public String getFood() {
        return food;
    }

    public String getRatedBy() {
        return ratedBy;
    }
}
