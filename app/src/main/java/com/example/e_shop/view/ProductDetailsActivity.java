package com.example.e_shop.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.e_shop.model.Product;
import com.example.e_shop.R;

public class ProductDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_PRODUCT = "productItem";
    private ImageView productDetailsImageView;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
      productDetailsImageView = findViewById(R.id.product_details_imageView);

      product = getIntent().getParcelableExtra(EXTRA_PRODUCT);
        createProductDetailsItem(product);
    }

    private void createProductDetailsItem(Product product) {
        Glide.with(this).load(product.getProductImage())
                .into(productDetailsImageView);
    }
}