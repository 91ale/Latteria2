package com.example.latteria;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository repository;
    private LiveData<List<Product>> allProducts;
    private LiveData<List<Product>> spesa;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
        allProducts = repository.getAllProducts();
    }

    public void insert(Product product) {
        repository.insert(product);
    }

    public void update(Product product) {
        repository.update(product);
    }

    public void delete(Product product) {
        repository.delete(product);
    }

    public LiveData<List<Product>> getProductFromBarcode(String barcode) {
        spesa = repository.getProductFromBarcode(barcode);
        return spesa;
    }

    public void deleteAllProducts() { repository.deleteAllProducts(); }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }


}
