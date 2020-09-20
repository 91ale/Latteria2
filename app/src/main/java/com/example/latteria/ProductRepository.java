package com.example.latteria;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ProductRepository {

    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;
    private LiveData<List<Product>> spesa;

    public ProductRepository(Application application) {
        ProductDatabase database = ProductDatabase.getInstance(application);
        productDao = database.productDao();
        allProducts = productDao.getAllProduct();
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

    public LiveData<List<Product>> getProductFromBarcode(String barcode) {
        new GetProductFromBarcodeAsyncTask(productDao).execute(barcode);
        return allProducts;
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
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

    private static class GetProductFromBarcodeAsyncTask extends AsyncTask<String, Void, Void> {
        private ProductDao productDao;

        private GetProductFromBarcodeAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(String... barcodes) {
            allProducts.getValue().add(productDao.getProductFromBarcode(barcodes[0]));
            return allProducts;
            productDao.insert(products[0]);
            return null;
        }
    }

}
