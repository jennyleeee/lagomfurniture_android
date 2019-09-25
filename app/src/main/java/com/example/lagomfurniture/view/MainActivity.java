package com.example.lagomfurniture.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lagomfurniture.R;
import com.example.lagomfurniture.model.Category;
import com.example.lagomfurniture.utils.SharedPreferencesUtils;
import com.example.lagomfurniture.utils.adapter.CategoryRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "메인";
    private static final int SELECT_ON_VIEW = 1;
    private static final int SELECT_OFF_VIEW = 0;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerview_category;
    private TextView productSizeText;
    private TextView textView_userEmail;
    private TextView textView_userNickname;
    private TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(R.layout.activity_main);
        viewInit();
        categoryRecyclerViewSetting();

        getUserInfo();
        logout(logout);
    }

    private void viewInit() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawerlayout);
        recyclerview_category = findViewById(R.id.recyclerview_category);
        productSizeText = findViewById(R.id.productSize);
        textView_userEmail = findViewById(R.id.textviewUserEmail);
        textView_userNickname = findViewById(R.id.textviewUserNickname);
        logout = findViewById(R.id.drawer_logout);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);    // 툴바에 프로젝트 이름 생성하지 않기.
            actionBar.setDisplayHomeAsUpEnabled(false);     // 뒤로가기 버튼 자동 생성해주는 코드
        }
    }

    private void categoryRecyclerViewSetting() {
        List<Category> items = new ArrayList<>();

        items.add(new Category("Bed", R.drawable.icon_bed, SELECT_ON_VIEW));
        items.add(new Category("Chest", R.drawable.icon_chest, SELECT_OFF_VIEW));
        items.add(new Category("Table", R.drawable.icon_table, SELECT_OFF_VIEW));
        items.add(new Category("Chair", R.drawable.icon_chair, SELECT_OFF_VIEW));
        items.add(new Category("Lamp", R.drawable.icon_lamp, SELECT_OFF_VIEW));

        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(items);
        LinearLayoutManager categoryLayoutManager = new LinearLayoutManager(MainActivity.this);
        categoryLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerview_category.setLayoutManager(categoryLayoutManager);
        recyclerview_category.setHasFixedSize(true);
        recyclerview_category.setAdapter(adapter);
    }

    private void getUserInfo() {
        Intent intent = getIntent();
        if (intent != null) {
            String userEmail = intent.getStringExtra("user_email");
            String userNickname = intent.getStringExtra("nickname");
            textView_userEmail.setText(userEmail);
            textView_userNickname.setText(userNickname);
        } else {
            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(MainActivity.this);
            String userEmail = sharedPreferencesUtils.getStringSharedPreferences("user_email");
            String userNickname = sharedPreferencesUtils.getStringSharedPreferences("nickname");
            Log.v(TAG,"USER EMAIL : " + userEmail);
            Log.v(TAG,"USER userNickname : " + userNickname);
            textView_userEmail.setText(userEmail);
            textView_userNickname.setText(userNickname);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shopping_basket:
                Toast.makeText(this, "장바구니 클릭", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.drawer_open:
                Toast.makeText(this, "세팅 버튼 클릭", Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(GravityCompat.END);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //종료 애니메이션 없애는 메소드
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    private void logout(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(MainActivity.this);
                sharedPreferencesUtils.clearSharedPreferences();
                finish();
            }
        });
    }
}
