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
import com.example.lagomfurniture.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "로그인";

    private TextView textViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        viewInit();

        LoginViewModel loginViewModel = ViewModelProviders.of(LoginActivity.this).get(LoginViewModel.class);

        activityLoginBinding.setLoginViewModel(loginViewModel);
        activityLoginBinding.setLifecycleOwner(this);

        loginViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.v(TAG, "onChanged");
                Log.v(TAG, "LoginActivity user : " + user);
                Toast.makeText(LoginActivity.this, "로그인성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void viewInit() {
        textViewRegister = findViewById(R.id.textviewRegister);
    }
}
