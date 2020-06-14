package com.example.boozingo.util;

import com.example.boozingo.interfaces.EndPoints;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static  String Base_Url="http://192.168.2.5:3000";
    private static  RetrofitClient mInstance;
    private Retrofit retrofit;
    private Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public RetrofitClient() {
        retrofit=new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static String getBase_Url() {
        return Base_Url;
    }

    public static void setBase_Url(String base_Url) {
        Base_Url = base_Url;
    }

    public static synchronized RetrofitClient getInstance(){
        if(mInstance==null)
            mInstance = new RetrofitClient();
        return mInstance;
    }

    public EndPoints getApi(){
        return retrofit.create(EndPoints.class);
    }
}
