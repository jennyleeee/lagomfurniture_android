package com.example.lagomfurniture.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.utils.retrofit.RetrofitAPI;
import com.example.lagomfurniture.utils.retrofit.RetrofitInit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private static final String TAG = "결";
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public LoginRepository() {
    }

    public void getUserMutableLiveData(MutableLiveData<String> userEmail, MutableLiveData<String> password) {
        RetrofitAPI retrofitAPI = RetrofitInit.getRetrofitAPI();
        Call<User> call = retrofitAPI.EmailLogin(userEmail.getValue(), password.getValue());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.v(TAG, "이메일 리스폰즈 : " + response.body().toString());
                Log.v(TAG, "이메일 리절트 코드  : " + response.body().getResultcode());
                if (response.body().getResponse().equals("success")) {
                    Log.v(TAG, "이메일 로그인 성공");
                    userMutableLiveData.setValue(response.body());

                }
                if (response.body().getResponse().equals("failed")) {
                    Log.v(TAG, "이메일 로그인 실패");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.v(TAG, "로그인 실패" + t);
            }
        });
    }
}
