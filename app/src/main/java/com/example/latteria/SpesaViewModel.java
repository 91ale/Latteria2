package com.example.latteria;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class SpesaViewModel extends AndroidViewModel {

    private ProductRepository repository;

    public SpesaViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
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

    public List<Product> getProductFromBarcode(String barcode) {
        return repository.getProductFromBarcode(barcode);
    }

    public void deleteAllProducts() { repository.deleteAllProducts(); }


}
