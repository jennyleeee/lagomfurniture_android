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

    public int getProductId() {
        return productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductExplained1() {
        return productExplained1;
    }

    public String getProductExplained2() {
        return productExplained2;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductThumbnail() {
//        String host = "http://172.30.1.23:8080";
        String host = "http://10.66.112.34:8080";
        String path = "/static/img/product/";
        String category = getProductCategory();
        productThumbnail = host + path + category + "/" + productThumbnail;
        return productThumbnail;
    }

    @BindingAdapter({"productThumbnail"})
    public static void loadProductImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .override(500, 500))  // 기본 옵션 세팅. 모든 이미지 로드에 값 적용
                .load(imageURL)
                .placeholder(R.color.colorGray)
                .into(imageView);
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productCategory='" + productCategory + '\'' + ", productExplained1='" + productExplained1 + '\'' + ", productExplained2='" + productExplained2 + '\'' + ", productName='" + productName + '\'' + ", productPrice='" + productPrice + '\'' + ", productThumbnail='" + productThumbnail + '\'' + '}';
    }
}
