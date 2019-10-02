package com.example.lagomfurniture.utils.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lagomfurniture.R;
import com.example.lagomfurniture.databinding.ProductListItemBinding;
import com.example.lagomfurniture.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListRcAdapter extends RecyclerView.Adapter<ProductListRcAdapter.ProductViewHolder> {

    private List<Product> products;

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ProductListItemBinding productListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.product_list_item, viewGroup, false);
        return new ProductViewHolder(productListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
        Product product = products.get(position);
        productViewHolder.productListItemBinding.setProduct(product);
    }

    @Override
    public int getItemCount() {
        if (products != null) {
            return products.size();
        } else {
            return 0;
        }
    }

    public void setProductList(ArrayList<Product>products){
        this.products = products;
        notifyDataSetChanged();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ProductListItemBinding productListItemBinding;

        public ProductViewHolder(@NonNull ProductListItemBinding productListItemBinding) {
            super(productListItemBinding.getRoot());
            this.productListItemBinding = productListItemBinding;
        }

    }


}
