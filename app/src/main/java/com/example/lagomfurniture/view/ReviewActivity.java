package com.example.lagomfurniture.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.lagomfurniture.R;

public class ReviewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Toolbar toolbar = findViewById(R.id.toolBar);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);    // 툴바에 프로젝트 이름 생성하지 않기.
            actionBar.setDisplayHomeAsUpEnabled(false);     // 뒤로가기 버튼 자동 생성해주는 코드
        }
    }

    //종료 애니메이션 없애는 메소드
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
