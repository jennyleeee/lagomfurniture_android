package com.example.lagomfurniture.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lagomfurniture.R;
import com.example.lagomfurniture.databinding.ActivityRegisterBinding;
import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "회원가입";
    TextView textviewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding activityRegisterBinding = DataBindingUtil.setContentView(this,R.layout.activity_register);

        viewInit();

        RegisterViewModel registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        activityRegisterBinding.setRegisterViewModel(registerViewModel);
        activityRegisterBinding.setLifecycleOwner(this);

        registerViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.v(TAG, "RegisterActivity : on Changed");
                if (user.getResponse().equals("success")) {
                    finish();
                }
            }
        });

        textviewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void viewInit() {
        textviewLogin = findViewById(R.id.textviewLogin);
    }

}
