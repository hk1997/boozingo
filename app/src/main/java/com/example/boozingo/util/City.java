package com.example.boozingo.util;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("city_name")
    String cityName;
    @SerializedName("city_image")
    String cityImage;
    @SerializedName("city_icon")
    String cityIcon;

    public String getCityName() {
        return cityName;
    }

    public String getCityImage() {
        return cityImage;
    }

    public String getCityIcon() {
        return cityIcon;
    }

    @NonNull
    @Override
    public String toString() {
        return cityName;
    }
}
