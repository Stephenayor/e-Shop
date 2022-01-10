package com.example.e_shop.repository;

import com.example.e_shop.model.CartItem;
import com.example.e_shop.model.Product;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CartRepository {
    private MutableLiveData<List<CartItem>> mutableCart = new MutableLiveData<>();

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

    public MutableLiveData<List<CartItem>> addCartItem (CartItem cartItem){
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);
        mutableCart.setValue(cartItemList);
        return mutableCart;
    }
}
