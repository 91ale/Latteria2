package com.example.latteria;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private ProductDao productDao;
    private List<Product> productList = new ArrayList<>();

    public ProductRepository(Application application) {
        ProductDatabase database = ProductDatabase.getInstance(application);
        productDao = database.productDao();
    }

    public void insert(Product product) {
        new InsertProductAsyncTask(productDao).execute(product);
    }

    public void update(Product product) {
        new UpdateProductAsyncTask(productDao).execute(product);
    }

    public void delete(Product product) {
        new DeleteProductAsyncTask(productDao).execute(product);
    }

    public void deleteAllProducts() {
        new DeleteAllProductsAsyncTask(productDao).execute();
    }

    public List<Product> getProductFromBarcode(String barcode) {
        productList.add(productDao.getProductFromBarcode(barcode));
        return productList;
    }

    public Product getSingleProductFromBarcode(String barcode) {
        return productDao.getProductFromBarcode(barcode);
    }

    private static class InsertProductAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        private InsertProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.insert(products[0]);
            return null;
        }
    }

    private static class UpdateProductAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        private UpdateProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.update(products[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        private DeleteProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.delete(products[0]);
            return null;
        }
    }

    private static class DeleteAllProductsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProductDao productDao;

        private DeleteAllProductsAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDao.deleteAllproducts();
            return null;
        }
    }

}
