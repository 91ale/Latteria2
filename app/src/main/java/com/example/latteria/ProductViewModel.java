package com.example.latteria;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository repository;
    private LiveData<List<Product>> allProducts;
    MediatorLiveData<List<Product>> liveDataMerger = new MediatorLiveData<>();
    List<Product> productsFromBarcode = new ArrayList<>();
    MutableLiveData<List<Product>> productsFromBarcodeMutable = new MutableLiveData<>();

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

    public MutableLiveData<List<Product>> getProductFromBarcode(String barcode) throws ExecutionException, InterruptedException {
        liveDataMerger.addSource(repository.getProductFromBarcode(barcode), new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                productsFromBarcode.addAll(products);
            }
        });
        productsFromBarcodeMutable.postValue(productsFromBarcode);
        return productsFromBarcodeMutable;
    }

    private static LiveData<List<Product>> mergeDataSources(LiveData<List<Product>>... sources) {
        MediatorLiveData<List<Product>> mergedSources = new MediatorLiveData<>();
        MediatorLiveData<List<Product>> merged = new MediatorLiveData<>();
        for (LiveData<List<Product>> source : sources) {
            merged.addSource(source, mergedSources::setValue);
        }
        return mergedSources;
    }

    public void deleteAllProducts() { repository.deleteAllProducts(); }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }


}
