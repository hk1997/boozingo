package com.example.boozingo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.boozingo.R;

public class Auth {
    private static final String key = "boozingoAuthToken";
    private static final String fileName="com.example.Boozingo.PREFERENCE_FILE_KEY";

    public static void setAccessToken(Context context,String token){
        SharedPreferences sharedPref = context.getSharedPreferences(fileName,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, token);
        editor.apply();
    }

    public static String getAccessToken(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(fileName,context.MODE_PRIVATE);
        String token = sharedPref.getString(key,null);
        return token;
    }

    public static void logout(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(fileName,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, null);
        editor.apply();
    }
}
