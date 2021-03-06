package com.example.e_shop.database;

import com.example.e_shop.model.CartItem;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToCart(CartItem cart);

    @Query("SELECT * FROM MyCart")
    LiveData<List<CartItem>> getCartList();

    @Delete
    void deleteProduct(CartItem cartItem);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEvents(CartItem cartItem);
}
