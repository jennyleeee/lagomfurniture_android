package com.example.lagomfurniture.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.utils.retrofit.RetrofitAPI;
import com.example.lagomfurniture.utils.retrofit.RetrofitInit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    private MutableLiveData<List<Product>> mutableLiveData = new MutableLiveData<>();

    public ProductRepository() {
    }

    public MutableLiveData<List<Product>> getMutableLiveData(String category) {
        RetrofitAPI retrofitAPI = RetrofitInit.getRetrofitAPI();
        Call<List<Product>> call = retrofitAPI.getProductList(category);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> productList = response.body();
                if (productList != null) {
                    mutableLiveData.setValue(productList);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
