package com.example.lagomfurniture.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import com.example.lagomfurniture.databinding.ActivityMainBinding;
import com.example.lagomfurniture.model.Category;
import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.utils.SharedPreferencesUtils;
import com.example.lagomfurniture.utils.adapter.MainCategoryRcAdapter;
import com.example.lagomfurniture.utils.adapter.ProductListRcAdapter;
import com.example.lagomfurniture.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainCategoryRcAdapter.OnItemClickListener{
    private static final String TAG = "메인";
    private static final int SELECT_ON_VIEW = 2;
    private static final int SELECT_OFF_VIEW = 1;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerview_category;
    private TextView productSizeText;
    private TextView textView_userEmail;
    private TextView textView_userNickname;
    private TextView logout;
    private ProductViewModel productViewModel;
    private ProductListRcAdapter productListRcAdapter;
    private MainCategoryRcAdapter mainCategoryRcAdapter;
    private List<Category> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewInit();
        CategoryRecyclerViewSetting();

        getUserInfo();
        logout(logout);


        //bind RecyclerView
        RecyclerView recyclerView = activityMainBinding.recyclerviewProduct;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);

        //bind data
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productListRcAdapter = new ProductListRcAdapter();
        recyclerView.setAdapter(productListRcAdapter);
        //메인화면은 클릭이벤트 없어도 bed로 받아와야 함
        getProductList("bed");
    }


    private void getProductList(String category) {
        productViewModel.getProduct(category).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productListRcAdapter.setProductList((ArrayList<Product>) products);
//                productSizeText.setText(products.size());
//                int productListSize = products.size();
                String productListSize = String.valueOf(products.size());
                productSizeText.setText(productListSize);
            }
        });
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

    private void CategoryRecyclerViewSetting() {
//        final List<Category> items = new ArrayList<>();

        items.add(new Category("Bed", R.drawable.icon_bed, SELECT_ON_VIEW));
        items.add(new Category("Chest", R.drawable.icon_chest, SELECT_OFF_VIEW));
        items.add(new Category("Table", R.drawable.icon_table, SELECT_OFF_VIEW));
        items.add(new Category("Chair", R.drawable.icon_chair, SELECT_OFF_VIEW));
        items.add(new Category("Lamp", R.drawable.icon_lamp, SELECT_OFF_VIEW));

        final MainCategoryRcAdapter adapter = new MainCategoryRcAdapter(items, MainActivity.this);
        LinearLayoutManager categoryLayoutManager = new LinearLayoutManager(MainActivity.this);
        categoryLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerview_category.setLayoutManager(categoryLayoutManager);
        recyclerview_category.setHasFixedSize(true);
        recyclerview_category.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
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
            Log.v(TAG, "USER EMAIL : " + userEmail);
            Log.v(TAG, "USER userNickname : " + userNickname);
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

    @Override
    public void onItemClick(Category category, int position) {
        Log.v("item Clicked ", "" + category);
        switch (category.getTitle()) {
            case "Bed":
                getProductList("bed");
                Log.v("bed, position: ", position+"");
                break;
            case "Chest":
                getProductList("chest");
                Log.v("chest,position: ", position+"");
                break;
            case "Table":
                getProductList("table");
                Log.v("table,position: ", position+"");
                break;
            case "Chair" :
                getProductList("chair");
                Log.v("chair,position: ", position+"");
                break;
            case "Lamp":
                getProductList("lamp");
                Log.v("lamp,position: ", position+"");
                break;
        }
    }
}
