package com.example.latteria;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Product.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {

    private static ProductDatabase instance;

    public abstract ProductDao productDao();

    public static  synchronized ProductDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProductDatabase.class, "product_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static  RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProductDao productDao;

        private PopulateDbAsyncTask(ProductDatabase db) {
            productDao = db.productDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDao.insert(new Product("1234567891011", "prodotto1", "descrizione1", 1.11));
            productDao.insert(new Product("8026624612773", "RESET FLUID 2 in 1", "correttore", 1.90));
            return null;
        }
    }
}
