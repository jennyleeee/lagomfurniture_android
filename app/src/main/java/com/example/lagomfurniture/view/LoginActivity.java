package com.example.lagomfurniture.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lagomfurniture.R;
import com.example.lagomfurniture.databinding.ActivityLoginBinding;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.utils.interfaces.LoginCallbacks;
import com.example.lagomfurniture.utils.SharedPreferencesUtils;
import com.example.lagomfurniture.utils.retrofit.RetrofitAPI;
import com.example.lagomfurniture.utils.retrofit.RetrofitInit;
import com.example.lagomfurniture.viewmodel.LoginViewModel;
import com.example.lagomfurniture.viewmodel.LoginViewModelFactory;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoginCallbacks {
    private static final String TAG = "로그인";
    private static final String SUCCESS = "success";
    private static final String EXIST = "exist";
    private TextView textViewRegister;

    // Kakao Login API
    private SessionCallback kakaoLoginSessionCallback;
    private ConstraintLayout kakaoLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        viewInit();
        // Kakao Login
        kakaoLoginSessionCallback = new SessionCallback();
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

        kakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session session = Session.getCurrentSession();
                session.open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);
                session.addCallback(kakaoLoginSessionCallback);
            }
        });
    }

    private void viewInit() {   // 뷰 선언
        textViewRegister = findViewById(R.id.textviewRegister);
        kakaoLoginBtn = findViewById(R.id.kakao_login_btn);
    }

    private void openActivity(User user) {  // 로그인 성공시 화면 전환
        Log.v(TAG, "OpenActivity");
        int resultCode = user.getResultcode();
        String userEmail = user.getUserEmail();
        String nickname = user.getNickname();
        if (!user.matchResultCode(resultCode)) {
            throw new IllegalArgumentException("로그인 상태를 확인해 주세요.");
        }
        if (user.getUserEmail() == null && user.getNickname() == null) {
            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(LoginActivity.this);
            userEmail = sharedPreferencesUtils.getStringSharedPreferences("user_email");
            nickname = sharedPreferencesUtils.getStringSharedPreferences("nickname");
        }

        Toast.makeText(LoginActivity.this, nickname + " 님 환영합니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("user_email", userEmail);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
        finish();
    }


    @Override
    public void onLoginFailure(String message) {    // 로그인 실패시 정규식 메세지 보내기
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    // 카카오
    private class SessionCallback implements ISessionCallback {
        /**
         * 로그인을 성공한 상태
         * access token을 성공적으로 발급 받아 valid access token을 가지고 있는 상태.
         * 일반적으로 로그인 후의 다음 activity로 이동한다.
         */
        @Override
        public void onSessionOpened() {
            Log.v(TAG,"세션 오픈");
            importKakaoLoginData();
        }

        /**
         * 로그인을 실패한 상태.
         * 세션이 만료된 경우와는 다르게 네트웤등 일반적인 에러로 오픈에 실패한경우 불린다.
         * @param exception 에러가 발생한 경우에 해당 exception.
         */
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.v(TAG,"세션 오픈 실패1 : " + exception.toString());
        }


        private void importKakaoLoginData() {
            List<String> keys = new ArrayList<>();
            keys.add("properties.nickname");
            keys.add("properties.profile_image");
            keys.add("kakao_account.email");

            UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    String message = "사용자 정보를 가지고 오는데 실패 했습니다.1 : " + errorResult;
                    Log.v(TAG, message);
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    String userEmail = result.getKakaoAccount().getEmail();
                    String id = String.valueOf(result.getId());
                    String nickname = result.getNickname();
                    String platform = "kakao";
                    String profileImage = result.getProfileImagePath();
                    Log.v(TAG, "Email : " + userEmail + " / id : " + id + " / nickname : " + nickname + " / profile : " + profileImage);
                    SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(LoginActivity.this);
                    sharedPreferencesUtils.setStringSharedPreferences("user_email",userEmail);
                    sharedPreferencesUtils.setStringSharedPreferences("nickname",nickname);
                    sharedPreferencesUtils.setIntSharedPreferences("user_id", Integer.parseInt(id));
                    setSNSUserConnect(userEmail, id, nickname, platform, profileImage);
                }

                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "사용자 정보를 가지고 오는데 실패 했습니다.2 : " + errorResult;
                    Log.v(TAG, message);
                }
            });
        }
        private void setSNSUserConnect(String userEmail, String password, String nickname, String platform, String profileImage) {
            RetrofitAPI retrofitAPI = RetrofitInit.getRetrofitAPI();
            Call<User> call = retrofitAPI.Register(userEmail,password,nickname,platform,profileImage);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.v(TAG,"카카오 로그인 성공 : " + response.body().getResponse());
                    if (response.body().getResponse().equals(SUCCESS) || response.body().getResponse().equals(EXIST)) {
                        User user = response.body();
                        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(LoginActivity.this);
                        sharedPreferencesUtils.setIntSharedPreferences("user_resultcode", user.getResultcode());
                        openActivity(user);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

        }

    }

}
