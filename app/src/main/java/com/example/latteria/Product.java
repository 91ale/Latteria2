package com.example.latteria;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_table")
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int productID;

    private String barcode;

    private String name;

    private String description;

    private double sellingPrice;

    public Product(String barcode, String name, String description, double sellingPrice) {
        this.barcode = barcode;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getProductID() {
        return productID;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }
}
