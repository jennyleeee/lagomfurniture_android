package com.example.lagomfurniture.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.example.lagomfurniture.R;
import com.example.lagomfurniture.utils.SharedPreferencesUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getHashKey();
        Handler splashHandler = new Handler();
        splashHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onAutoLogin();

            }
        }, 1000);
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

    private void getHashKey() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}
