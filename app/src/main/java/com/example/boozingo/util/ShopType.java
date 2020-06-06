package com.example.boozingo.util;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ShopType {

    @SerializedName("type_id")
    String typeId;
    @SerializedName("type")
    String type;
    @SerializedName("label")
    String label;

    public String getTypeId() {
        return typeId;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    @NonNull
    @Override
    public String toString() {
        return label;
    }
}
