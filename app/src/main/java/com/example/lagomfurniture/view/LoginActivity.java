package com.example.lagomfurniture.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lagomfurniture.R;
import com.example.lagomfurniture.databinding.ActivityLoginBinding;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.utils.LoginCallbacks;
import com.example.lagomfurniture.utils.SharedPreferencesUtils;
import com.example.lagomfurniture.viewmodel.LoginViewModel;
import com.example.lagomfurniture.viewmodel.LoginViewModelFactory;

public class LoginActivity extends AppCompatActivity implements LoginCallbacks {
    private static final String TAG = "로그인";
    private static final String SUCCESS = "success";

    private TextView textViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        viewInit();

        LoginViewModel loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory(this)).get(LoginViewModel.class);

        activityLoginBinding.setLoginViewModel(loginViewModel);
        activityLoginBinding.setLifecycleOwner(this);

        loginViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.v(TAG, "onChanged");
                if (user.getResponse().equals(SUCCESS)) {
                    Log.v(TAG, "LiveData User : "+user.toString());
                    SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(LoginActivity.this);
                    sharedPreferencesUtils.setStringSharedPreferences("user_email",user.getUserEmail());
                    sharedPreferencesUtils.setStringSharedPreferences("nickname",user.getNickname());
                    sharedPreferencesUtils.setIntSharedPreferences("user_id", user.getId());
                    sharedPreferencesUtils.setIntSharedPreferences("user_resultcode", user.getResultcode());
                    openActivity(user);
                }
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {    // 회원가입 페이지 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void viewInit() {   // 뷰 선언
        textViewRegister = findViewById(R.id.textviewRegister);
    }

    private void openActivity(User user) {  // 로그인 성공시 화면 전환
        Log.v(TAG, "OpenActivity");
        int resultCode = user.getResultcode();
        if (!user.matchResultCode(resultCode)) {
            throw new IllegalArgumentException("로그인 상태를 확인해 주세요.");
        }
        Toast.makeText(LoginActivity.this, user.getNickname() + " 님 환영합니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("user_email", user.getUserEmail());
        Log.v(TAG, "Send email : " + user.getUserEmail());
        intent.putExtra("nickname",user.getNickname());
        Log.v(TAG, "Send nickname : " + user.getNickname());
        startActivity(intent);
        finish();
    }


    @Override
    public void onLoginFailure(String message) {    // 로그인 실패시 정규식 메세지 보내기
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
