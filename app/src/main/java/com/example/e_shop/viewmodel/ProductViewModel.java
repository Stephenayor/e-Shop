package com.example.e_shop.viewmodel;

import com.example.e_shop.CartDatabase;
import com.example.e_shop.model.CartItem;
import com.example.e_shop.model.Product;
import com.example.e_shop.repository.CartRepository;
import com.example.e_shop.repository.ProductRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository;
    private MutableLiveData<Product> mutableLiveData;
    CartRepository cartRepository = new CartRepository();


    public ProductViewModel() {
        productRepository = new ProductRepository();
    }

    public LiveData<Product> getProducts() {
        if(mutableLiveData==null){
            mutableLiveData = productRepository.requestProducts();
        }
        return mutableLiveData;
    }

    public LiveData<List<CartItem>> getCart(){
        return cartRepository.getCart();
    }

    public void addProductToCart(CartItem cartItem){
        cartRepository.addProductToCart(cartItem);
    }

    public LiveData<List<CartItem>> getCartItem(CartItem cartItem){
        return cartRepository.returnCartItemList(cartItem);
    }
}
