package com.example.e_shop;

import android.content.Context;
import com.example.e_shop.model.CartItem;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {CartItem.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "CartItemList";
    private static final String LOG_TAG = CartDatabase.class.getSimpleName();
    private static  CartDatabase eventsInstance;

    public static CartDatabase getInstance(Context context){
        if (eventsInstance == null){
            synchronized (LOCK){
                eventsInstance = Room.databaseBuilder(context.getApplicationContext(),
                        CartDatabase.class,CartDatabase.DATABASE_NAME)
                        //Queries are been done in a separate thread to avoid locking the UI
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return eventsInstance;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {
    }
}
