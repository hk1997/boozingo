package com.example.boozingo.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.boozingo.models.ResponseShopSummary;
import com.example.boozingo.util.RetrofitClient;
import com.example.boozingo.util.Summary;
import com.example.boozingo.util.ShopType;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ShopType> shopType;
    private MutableLiveData<ArrayList<Summary>> shopSummaryList;

    public HomeViewModel() {
        shopType = new MutableLiveData<>();
        shopSummaryList = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Summary>> getShopSummaryList() {
        if(shopSummaryList.getValue()==null){
            requestSummaryList();
        }
        return shopSummaryList;
    }

    public MutableLiveData<ShopType> getShopType() {
        return shopType;
    }

    public void requestSummaryList(){
        String type = shopType.getValue()==null?"null":shopType.getValue().getTypeId();
        Call<ResponseShopSummary> call = RetrofitClient.getInstance().getApi().listShopSummaries();
        call.enqueue(new Callback<ResponseShopSummary>() {
            @Override
            public void onResponse(Call<ResponseShopSummary> call, Response<ResponseShopSummary> response) {
                if(response.code()==200){
                    shopSummaryList.setValue(response.body().getData().getListRestaurantSummaries());
                }
            }

            @Override
            public void onFailure(Call<ResponseShopSummary> call, Throwable t) {

            }
        });
    }
}