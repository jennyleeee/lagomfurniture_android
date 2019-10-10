package com.example.lagomfurniture.view;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.lagomfurniture.R;
import com.example.lagomfurniture.utils.SharedPreferencesUtils;

// Navigation Drawer 재사용을 위한 BseActivity
public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Toolbar Menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu, menu);
        getUserInfo();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   // Toolbar Menu
        DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
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

    private void getUserInfo() {    // 유저 이메일, 닉네임 가져오기
        TextView textView_userEmail = findViewById(R.id.textviewUserEmail);
        TextView textView_userNickname = findViewById(R.id.textviewUserNickname);

        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("user_email");
        String userNickname = intent.getStringExtra("nickname");
        if (userEmail == null && userNickname == null) {
            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this);
            userEmail = sharedPreferencesUtils.getStringSharedPreferences("user_email");
            userNickname = sharedPreferencesUtils.getStringSharedPreferences("nickname");
        }
        textView_userEmail.setText(userEmail);
        textView_userNickname.setText(userNickname);
    }
}
