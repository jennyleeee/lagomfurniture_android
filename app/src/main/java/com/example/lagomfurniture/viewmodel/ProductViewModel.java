package com.example.lagomfurniture.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.lagomfurniture.model.Product;
import com.example.lagomfurniture.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository productRepository;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository();
    }
    public LiveData<List<Product>> getProduct(String category){
        return productRepository.getMutableLiveData(category);
    }
}