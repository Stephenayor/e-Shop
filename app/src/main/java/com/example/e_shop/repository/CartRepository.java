package com.example.e_shop.repository;

import android.content.Context;

import com.example.e_shop.database.CartDatabase;
import com.example.e_shop.model.CartItem;
import com.example.e_shop.model.Product;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CartRepository {
    private MutableLiveData<List<CartItem>> mutableCart = new MutableLiveData<>();
    public CartDatabase cartDatabase;
    Context context;
    private LiveData<List<CartItem>> cartItemListLivedata;
    private MutableLiveData<Double> mutableTotalCartItemPrice = new MutableLiveData<>();


    public LiveData<List<CartItem>> getCart() {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        return mutableCart;
    }

    public void initCart() {
        mutableCart.setValue(new ArrayList<CartItem>());
    }

    public List<CartItem> addItemToCart(Product product) {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
        CartItem cartItem = new CartItem(product, 1);
        cartItemList.add(cartItem);
        return cartItemList;
    }

    public void addProductToCart(CartItem cartItem) {
        cartDatabase = CartDatabase.getInstance(context);
        cartDatabase.cartDao().addToCart(cartItem);
    }

    public void removeProductFromCart(CartItem cartItem) {
        cartDatabase = CartDatabase.getInstance(context);
        cartDatabase.cartDao().deleteProduct(cartItem);
    }

    public LiveData<List<CartItem>> returnCartItemList(CartItem cartItem) {
        cartDatabase = CartDatabase.getInstance(context);
        cartItemListLivedata = cartDatabase.cartDao().getCartList();
        return cartItemListLivedata;
    }

    public LiveData<Double> getTotalPrice(List<CartItem> cartItemList) {
        if (mutableTotalCartItemPrice.getValue() == null) {
            mutableTotalCartItemPrice.setValue(0.0);
        }
        double total = 0.0;
        for (CartItem cartItem : cartItemList) {
            String cartItemPrice = cartItem.getProduct().getProductPrice();
            String cartItemPriceSubString = cartItemPrice.substring(1);
            total += Integer.parseInt(cartItemPriceSubString) * cartItem.getQuantity();
        }
        mutableTotalCartItemPrice.setValue(total);
        return mutableTotalCartItemPrice;
    }
}
