package com.example.boozingo.interfaces;

import com.example.boozingo.models.ApiResponse;
import com.example.boozingo.models.ResponseListCities;
import com.example.boozingo.models.ResponseListShopTypes;
import com.example.boozingo.models.ResponseShopSummary;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EndPoints {
    @FormUrlEncoded
    @POST("/api/login")
    Call<ApiResponse> login(
            @Field("userName") String userName,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/forgot_password")
    Call<ApiResponse> forgotPassword(
      @Field("userName") String userName,
      @Field("password") String password,
      @Field("confirmPassword") String confirmPassword
    );

    @FormUrlEncoded
    @POST("/api/is_unique")
    Call<ApiResponse> checkUserName(
            @Field("userName") String userName
    );

    @FormUrlEncoded
    @POST("/api/is_unique-email")
    Call<ApiResponse> checkEmail(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("/api/is_unique-phone")
    Call<ApiResponse> checkPhone(
            @Field("phone_num") String phone
    );

    @FormUrlEncoded
    @POST("/api/register")
    Call<ApiResponse> register(
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("userName") String userName,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirmpassword") String confirmpassword,
            @Field("dob") String dob,
            @Field("gender") String gender
    );

    @POST("/api/list-cities")
    Call<ResponseListCities> listCities(

    );

    @POST("/api/list-shop-types")
    Call<ResponseListShopTypes> listShopTypes();


    @POST("/api/list")
    Call<ResponseShopSummary> listShopSummaries(

    );

    @FormUrlEncoded
    @POST("/api/get_details")
    Call<ResponseShopSummary> getShopDetails(
        @Field("bar_id") String barId
    );
}
