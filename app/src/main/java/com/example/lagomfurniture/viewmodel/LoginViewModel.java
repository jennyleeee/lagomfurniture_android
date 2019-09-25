package com.example.lagomfurniture.viewmodel;

import android.os.AsyncTask;
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

public class LoginViewModel extends ViewModel {

    public static final String TAG = "로그인";
    private static final String SUCCESS = "success";

    public MutableLiveData<String> userEmail = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<Integer> loginLoading;

    public LoginViewModel() {
    }

    public MutableLiveData<Integer> getLoginLoading() {
        if (loginLoading == null) {
            loginLoading = new MutableLiveData<>();
            loginLoading.setValue(8);   // 8 = View.GONE
        }
        return loginLoading;
    }

    public LiveData<User> getUser() {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public void getServerConnect() {
        getLoginLoading().setValue(0);  // 0 = View.VISIBLE
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginLoading.setValue(8);
                getUserData();
            }
        },1000);
    }

    public void getUserData() {
        // 리팩토링 실패 : 레퍼지토리에서 레트로핏 통신을 하려고하면 레트로핏 API 통신은 비동기 처리인데,
        // 메소드가 동기 처리를 하여 MutableLiveData<User> 반환이 안됨. 나중에 코틀린, MVVM 공부할 시에 다시 공부
        RetrofitAPI retrofitAPI = RetrofitInit.getRetrofitAPI();
        Call<User> call = retrofitAPI.EmailLogin(userEmail.getValue() , password.getValue());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.v(TAG, "이메일 리스폰즈 : " + response.body().toString());
                if (response.body().getResponse().equals(SUCCESS)) {
                    Log.v(TAG, "이메일 로그인 성공");
                    userMutableLiveData.setValue(response.body());
                }
                if (response.body().getResponse().equals("failed")) {
                    //loginCallbacks.onLoginFailure("이메일 및 비밀번호를 확인해 주세요."); 이부분 예외처리 아직 안함
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.v(TAG, "로그인 실패" + t);
            }
        });
    }

}
