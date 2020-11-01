package com.example.latteria.ui.Insert;

import android.app.Application;

import com.example.latteria.Product;
import com.example.latteria.ProductRepository;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class InsertViewModel extends AndroidViewModel {

    private ProductRepository repository;

    public InsertViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
    }

    public Product getSingleProductFromBarcode(String barcode) {
        return repository.getSingleProductFromBarcode(barcode);
    }

    public void insert(Product product) {
        repository.insert(product);
    }

}