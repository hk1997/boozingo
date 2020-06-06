package com.example.boozingo.models;

import com.example.boozingo.util.ShopType;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseListShopTypes {
    @SerializedName("message")
    String message;
    @SerializedName("data")
    ListShopType data;

    public String getMessage() {
        return message;
    }

    public ListShopType getData() {
        return data;
    }

    public class ListShopType{
        @SerializedName("type")
        ArrayList<ShopType> shopTypes;

        public ArrayList<ShopType> getShopTypes() {
            return shopTypes;
        }
    }
}
