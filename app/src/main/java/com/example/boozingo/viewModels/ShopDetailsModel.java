package com.example.boozingo.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.boozingo.models.ResponseShopSummary;
import com.example.boozingo.util.RetrofitClient;
import com.example.boozingo.util.Summary;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopDetailsModel extends ViewModel {
    private MutableLiveData<String> shopId;
    private MutableLiveData<Summary> shopSummary;

    public ShopDetailsModel() {
        shopId = new MutableLiveData<>();
        shopSummary = new MutableLiveData<>();
    }

    public MutableLiveData<String> getShopId() {
        return shopId;
    }

    public MutableLiveData<Summary> getShopSummary() {
        if(shopSummary.getValue()==null){
            fetchShopSummary(shopId.getValue());
        }
        return shopSummary;
    }

    public  void fetchShopSummary(String fetchId){

        Call<ResponseShopSummary> call = RetrofitClient.getInstance().getApi().getShopDetails(fetchId);
        call.enqueue(new Callback<ResponseShopSummary>() {
            @Override
            public void onResponse(Call<ResponseShopSummary> call, Response<ResponseShopSummary> response) {
                if(response.code()==200){
                    ArrayList<Summary> l= response.body().getData().getListRestaurantSummaries();
                    if(l.size()==1){
                        shopSummary.setValue(l.get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseShopSummary> call, Throwable t) {

            }
        });

    }
}
