package com.example.e_shop.viewmodel;

import com.example.e_shop.model.Product;
import com.example.e_shop.ProductRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository;
    private MutableLiveData<Product> mutableLiveData;

    public ProductViewModel() {
        productRepository = new ProductRepository();
    }

    public LiveData<Product> getPopularMovies() {
        if(mutableLiveData==null){
            mutableLiveData = productRepository.requestProducts();
        }
        return mutableLiveData;
    }
}
