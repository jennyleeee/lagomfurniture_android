package com.example.lagomfurniture.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import com.example.lagomfurniture.R;
import com.example.lagomfurniture.databinding.ActivityMainBinding;
import com.example.lagomfurniture.model.Category;
import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.utils.SharedPreferencesUtils;
import com.example.lagomfurniture.utils.adapter.MainCategoryRcAdapter;
import com.example.lagomfurniture.utils.adapter.ProductListRcAdapter;
import com.example.lagomfurniture.utils.interfaces.OnItemClickListener;
import com.example.lagomfurniture.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnItemClickListener {
    private static final String TAG = "메인";
    private static final int SELECT_ON_VIEW = 2;
    private static final int SELECT_OFF_VIEW = 1;
    private RecyclerView recyclerview_category;
    private TextView productSizeText;
    private TextView logout;
    private TextView review;
    private ProductViewModel productViewModel;
    private ProductListRcAdapter productListRcAdapter;
    private MainCategoryRcAdapter mainCategoryRcAdapter;
    private List<Category> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewInit();
        categoryRecyclerViewSetting();

        logout(logout);
        review(review);

        // bind RecyclerView
        RecyclerView recyclerView = activityMainBinding.recyclerviewProduct;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);

        // bind ViewModel
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productListRcAdapter = new ProductListRcAdapter();
        recyclerView.setAdapter(productListRcAdapter);

        //메인화면은 클릭이벤트 없어도 bed로 받아와야 함
        getProductList("bed");
        categoryState(SELECT_ON_VIEW, SELECT_OFF_VIEW, SELECT_OFF_VIEW, SELECT_OFF_VIEW, SELECT_OFF_VIEW);
    }

    private void getProductList(String category) {  // 상품 리스트 서버에서 가져오기
        productViewModel.getProduct(category).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productListRcAdapter.setProductList((ArrayList<Product>) products);
                productSizeText.setText(String.valueOf(products.size()));
            }
        });
    }

    private void viewInit() {   // 뷰 초기화
        Toolbar toolbar = findViewById(R.id.toolBar);
        recyclerview_category = findViewById(R.id.recyclerview_category);
        productSizeText = findViewById(R.id.productSize);
        logout = findViewById(R.id.drawer_logout);
        review = findViewById(R.id.Review);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);    // 툴바에 프로젝트 이름 생성하지 않기.
            actionBar.setDisplayHomeAsUpEnabled(false);     // 뒤로가기 버튼 자동 생성해주는 코드
        }
    }

    private void categoryRecyclerViewSetting() {    // 카테고리 항목 리사이클러뷰 세팅
        items.add(new Category(Category.Bed, R.drawable.icon_bed, SELECT_ON_VIEW));
        items.add(new Category(Category.Chest, R.drawable.icon_chest, SELECT_OFF_VIEW));
        items.add(new Category(Category.Table, R.drawable.icon_table, SELECT_OFF_VIEW));
        items.add(new Category(Category.Chair, R.drawable.icon_chair, SELECT_OFF_VIEW));
        items.add(new Category(Category.Lamp, R.drawable.icon_lamp, SELECT_OFF_VIEW));

        mainCategoryRcAdapter = new MainCategoryRcAdapter(items, MainActivity.this);
        LinearLayoutManager categoryLayoutManager = new LinearLayoutManager(MainActivity.this);
        categoryLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerview_category.setLayoutManager(categoryLayoutManager);
        recyclerview_category.setHasFixedSize(true);
        recyclerview_category.setAdapter(mainCategoryRcAdapter);
        mainCategoryRcAdapter.setOnItemClickListener(this);
    }

    private void categoryState(int bedState, int ChestState, int TableState, int ChairState, int LampState) {   // 카테고리 항목 리사이클러뷰 클릭시 뷰 상태 변경
        items.set(0, new Category(Category.Bed, R.drawable.icon_bed, bedState));
        items.set(1, new Category(Category.Chest, R.drawable.icon_chest, ChestState));
        items.set(2, new Category(Category.Table, R.drawable.icon_table, TableState));
        items.set(3, new Category(Category.Chair, R.drawable.icon_chair, ChairState));
        items.set(4, new Category(Category.Lamp, R.drawable.icon_lamp, LampState));
    }

    //종료 애니메이션 없애는 메소드
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    private void logout(View view) {    // 로그아웃 기능
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(MainActivity.this);
                sharedPreferencesUtils.clearSharedPreferences();
                finish();
            }
        });
    }

    private void review(View view) {    // 리뷰페이지 이동
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(Category category, int position) {
        Log.v("item Clicked ", "" + category);
        switch (category.getTitle()) {
            case Category.Bed :
                getProductList("bed");
                categoryState(SELECT_ON_VIEW, SELECT_OFF_VIEW, SELECT_OFF_VIEW, SELECT_OFF_VIEW, SELECT_OFF_VIEW);
                mainCategoryRcAdapter.notifyDataSetChanged();
                Log.v(TAG, "bed - position : " + position + "");
                break;
            case Category.Chest:
                getProductList("chest");
                categoryState(SELECT_OFF_VIEW, SELECT_ON_VIEW, SELECT_OFF_VIEW, SELECT_OFF_VIEW, SELECT_OFF_VIEW);
                mainCategoryRcAdapter.notifyDataSetChanged();
                Log.v(TAG, "chest - position : " + position + "");
                break;
            case Category.Table:
                getProductList("table");
                categoryState(SELECT_OFF_VIEW, SELECT_OFF_VIEW, SELECT_ON_VIEW, SELECT_OFF_VIEW, SELECT_OFF_VIEW);
                mainCategoryRcAdapter.notifyDataSetChanged();
                Log.v(TAG, "table - position : " + position + "");
                break;
            case Category.Chair:
                getProductList("chair");
                categoryState(SELECT_OFF_VIEW, SELECT_OFF_VIEW, SELECT_OFF_VIEW, SELECT_ON_VIEW, SELECT_OFF_VIEW);
                mainCategoryRcAdapter.notifyDataSetChanged();
                Log.v(TAG, "chair - position : " + position + "");
                break;
            case Category.Lamp:
                getProductList("lamp");
                categoryState(SELECT_OFF_VIEW, SELECT_OFF_VIEW, SELECT_OFF_VIEW, SELECT_OFF_VIEW, SELECT_ON_VIEW);
                mainCategoryRcAdapter.notifyDataSetChanged();
                Log.v(TAG, "lamp - position : " + position + "");
                break;
        }
    }
}
