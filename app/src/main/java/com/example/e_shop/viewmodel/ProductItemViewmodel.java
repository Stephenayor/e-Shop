package com.example.e_shop.viewmodel;

import com.example.e_shop.model.Product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductItemViewmodel extends ViewModel {
        private final MutableLiveData<Product> selectedProduct = new MutableLiveData<Product>();
        public void selectItem(Product item) {
            selectedProduct.setValue(item);
        }
        public LiveData<Product> getSelectedProduct() {
            return selectedProduct;
        }

}
