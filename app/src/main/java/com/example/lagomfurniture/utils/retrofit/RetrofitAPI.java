package com.example.lagomfurniture.utils.retrofit;

import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {


    @FormUrlEncoded
    @POST("/users/android_register")
    Call<User> Register(@Field("userEmail") String userEmail,
                        @Field("password") String password,
                        @Field("nickname") String nickname,
                        @Field("platform") String platform,
                        @Field("profileImage") String profileImage);

    @FormUrlEncoded
    @POST("/users/android_login")
    Call<User> EmailLogin(@Field("userEmail") String userEmail,
                          @Field("password") String password);

    @GET("/product/category_android")
    Call<List<Product>> getProductList (@Query("category") String category);
}
