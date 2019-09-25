package com.example.lagomfurniture.viewmodel;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.utils.retrofit.RetrofitAPI;
import com.example.lagomfurniture.utils.retrofit.RetrofitInit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private static final String TAG = "회원가입";
    private static final String PLATFORM_EMAIL = "email";
    private static final String SUCCESS = "success";
    private static final String DEFAULT_PROFILE_IMAGE = "icon_profile.png";
    public MutableLiveData<String> userEmail = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> nickname = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<Integer> registerLoading;

    public RegisterViewModel() {
    }

    public MutableLiveData<Integer> getRegisterLoading() {
        if (registerLoading == null) {
            registerLoading = new MutableLiveData<>();
            registerLoading.setValue(8);    // 8 = View.GONE
        }
        return registerLoading;
    }

    public LiveData<User> getUser() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public void getServerConnect() {
        getRegisterLoading().setValue(0);   // 0 = View.VISIBLE
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registerLoading.setValue(8);
                setUserData();
            }
        }, 1000);
    }


    private void setUserData() {
        String host = RetrofitInit.BASE_URL;
        String path = "/static/reviewimage/";
        String PROFILE_IMAGE = host + path + DEFAULT_PROFILE_IMAGE;
        RetrofitAPI retrofitAPI = RetrofitInit.getRetrofitAPI();
        Call<User> call = retrofitAPI.Register(userEmail.getValue(), password.getValue(), nickname.getValue(), PLATFORM_EMAIL, PROFILE_IMAGE);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals(SUCCESS)) {
                    Log.v(TAG, "회원가입 성공 : " + response.body());
                    userMutableLiveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.v(TAG, "회원가입 실패 : " + t);
            }
        });
    }
}
