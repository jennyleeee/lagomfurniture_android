package com.example.lagomfurniture.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lagomfurniture.R;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("productId")
    private int productId;
    @SerializedName("productCategory")
    private String productCategory;
    @SerializedName("productExplained1")
    private String productExplained1;
    @SerializedName("productExplained2")
    private String productExplained2;
    @SerializedName("productName")
    private String productName;
    @SerializedName("productPrice")
    private String productPrice;
    @SerializedName("productThumnail")
    private String productThumbnail;

    @BindingAdapter({"productThumbnail"})
    public static void loadProductImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .override(500,500))  // 기본 옵션 세팅. 모든 이미지 로드에 값 적용
                .load(imageURL)
                .placeholder(R.color.colorGray)
                .into(imageView);
    }

}
