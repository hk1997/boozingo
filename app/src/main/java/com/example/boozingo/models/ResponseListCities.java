package com.example.boozingo.models;

import com.example.boozingo.util.City;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseListCities {
    @SerializedName("message")
    String message;
    @SerializedName("data")
    ListCities data;

    public String getMessage() {
        return message;
    }

    public ListCities getData() {
        return data;
    }

    public class ListCities{
        @SerializedName("cities")
        ArrayList<City> cityList;

        public ArrayList<City> getCityList() {
            return cityList;
        }
    }
}
