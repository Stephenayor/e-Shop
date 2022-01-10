package com.example.e_shop.repository;

import android.content.Context;
import android.os.Build;

import com.example.e_shop.CartDatabase;
import com.example.e_shop.model.CartItem;
import com.example.e_shop.model.Product;
import com.example.e_shop.threads.Executor;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CartRepository {
    private MutableLiveData<List<CartItem>> mutableCart = new MutableLiveData<>();
    public CartDatabase cartDatabase;
    Context context;
    private LiveData<List<CartItem>> cartItemListLivedata;


    public LiveData<List<CartItem>> getCart() {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        return mutableCart;
    }

    public void initCart() {
        mutableCart.setValue(new ArrayList<CartItem>());
    }

    public  List<CartItem> addItemToCart(Product product){
        if (mutableCart.getValue() == null){
            initCart();
        }
        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
        CartItem cartItem = new CartItem(product, 1);
        cartItemList.add(cartItem);
        return cartItemList;
    }

    public void addProductToCart(CartItem cartItem){
    cartDatabase = CartDatabase.getInstance(context);
    cartDatabase.cartDao().addToCart(cartItem);

    }

    public LiveData<List<CartItem>> returnCartItemList(CartItem cartItem){
        cartDatabase = CartDatabase.getInstance(context);
        cartItemListLivedata = cartDatabase.cartDao().getCartList();
        return cartItemListLivedata;
    }
}
