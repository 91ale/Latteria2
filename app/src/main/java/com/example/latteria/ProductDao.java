package com.example.latteria;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProductDao {

    @Insert
    void insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM product_table")
    void deleteAllproducts();

    @Query("SELECT * FROM product_table")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM product_table WHERE barcode = :barcode")
    LiveData<List<Product>> getProductFromBarcode(String barcode);


}