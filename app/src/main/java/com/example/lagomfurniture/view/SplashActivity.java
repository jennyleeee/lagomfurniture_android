package com.example.lagomfurniture.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.lagomfurniture.R;
import com.example.lagomfurniture.utils.SharedPreferencesUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler splashHandler = new Handler();
        splashHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onAutoLogin();
            }
        }, 500);
    }

    private void onAutoLogin() {
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(SplashActivity.this);
        String userEmail = sharedPreferencesUtils.getStringSharedPreferences("user_email");
        int userResultCode = sharedPreferencesUtils.getIntSharedPreferences("user_resultcode");

        if (userEmail != null && userResultCode != 0) {
            // 로그인 기록이 있을 경우 메인 화면으로 이동
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // 로그인 기록이 없을 경우 로그인 화면으로 이동
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
