package com.example.latteria;

import android.app.Application;

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
    private LiveData<List<Product>> productsFromBarcode;
    MediatorLiveData<List<Product>> liveDataMerger = new MediatorLiveData<>();

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

    public LiveData<List<Product>> getProductFromBarcode(String barcode) throws ExecutionException, InterruptedException {
        //unire productsFromBarcode esistente al livedata restituito da repository.getProductFromBarcode(barcode)
        //productsFromBarcode = repository.getProductFromBarcode(barcode);
        LiveData<List<Product>> tempProductData = liveDataMerger;
        liveDataMerger.addSource(repository.getProductFromBarcode(barcode), value -> liveDataMerger.setValue(value));
        //liveDataMerger.addSource(tempProductData, value -> liveDataMerger.setValue(value));
        liveDataMerger.addSource(tempProductData, new Observer<List<Product>>() {
            private int count = 1;
            @Override
            public void onChanged(@Nullable List<Product> products) {
                count++;
                liveDataMerger.setValue(products);
                if (count > 3) {
                    liveDataMerger.removeSource(tempProductData);
                }
            }
        });

        //productsFromBarcode = mergeDataSources(repository.getProductFromBarcode(barcode), productsFromBarcode);
        return liveDataMerger;
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
