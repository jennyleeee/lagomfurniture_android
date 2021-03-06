package com.example.lagomfurniture.utils.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInit {

    public static final String BASE_URL ="http://54.180.193.22:8080";
    //"http://54.180.193.22";
    // 172.30.1.26
    // 10.66.112.34

    private static Retrofit getRetrofitInit() {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static RetrofitAPI getRetrofitAPI() {
        return getRetrofitInit().create(RetrofitAPI.class);
    }
}
