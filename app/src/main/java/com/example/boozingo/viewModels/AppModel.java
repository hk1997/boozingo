package com.example.boozingo.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.boozingo.models.ResponseListCities;
import com.example.boozingo.models.ResponseListShopTypes;
import com.example.boozingo.util.City;
import com.example.boozingo.util.RetrofitClient;
import com.example.boozingo.util.ShopType;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppModel extends ViewModel {
    MutableLiveData<String> cityName;
    MutableLiveData<ArrayList<City>> cityList;
    MutableLiveData<ArrayList<ShopType>> shopTypeList;

    public AppModel(){
        cityName = new MutableLiveData<>();
        cityList = new MutableLiveData<>();
        shopTypeList = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<ShopType>> getShopTypeList() {
        if(shopTypeList.getValue()==null){
            requestShopTypeList();
        }
        return shopTypeList;
    }

    public MutableLiveData<String> getCityName() {
        return cityName;
    }

    public MutableLiveData<ArrayList<City>> getCityList() {
        if(cityList.getValue()==null){
            requestCityList();
        }
        return cityList;
    }

    private void requestCityList(){
        Call<ResponseListCities> call = RetrofitClient.getInstance().getApi().listCities();
        call.enqueue(new Callback<ResponseListCities>() {
            @Override
            public void onResponse(Call<ResponseListCities> call, Response<ResponseListCities> response) {
                if(response.code()==200){
                    cityList.setValue(response.body().getData().getCityList());
                }
            }

            @Override
            public void onFailure(Call<ResponseListCities> call, Throwable t) {

            }
        });
    }
    private void requestShopTypeList(){
        Call<ResponseListShopTypes> call = RetrofitClient.getInstance().getApi().listShopTypes();
        call.enqueue(new Callback<ResponseListShopTypes>() {
            @Override
            public void onResponse(Call<ResponseListShopTypes> call, Response<ResponseListShopTypes> response) {
                if(response.code()==200){
                    shopTypeList.setValue(response.body().getData().getShopTypes());
                }
            }

            @Override
            public void onFailure(Call<ResponseListShopTypes> call, Throwable t) {

            }
        });
    }
}
