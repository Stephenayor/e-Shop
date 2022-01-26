package com.example.e_shop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_shop.model.Product;
import com.example.e_shop.R;

public class ProductDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_PRODUCT = "productItem";
    private ImageView productDetailsImageView;
    private TextView productDetailsTitleTextView;
    private TextView productDetailsPriceTextView;
    private Product product;
    private Button addToCartButton;
    private Fragment cartFragment;
    private int cartProductQuantity;
    private Button increaseQuantityButton, reduceQuantityButton, quantityTextButton;
    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
      productDetailsImageView = findViewById(R.id.product_details_imageView);
      productDetailsTitleTextView = findViewById(R.id.product_details_titleView);
      productDetailsPriceTextView = findViewById(R.id.product_details_priceView);
      addToCartButton = findViewById(R.id.cart_button);
      increaseQuantityButton = findViewById(R.id.add_button);
      reduceQuantityButton = findViewById(R.id.subtract_button);
      quantityTextButton = findViewById(R.id.quantity_button_text);

      
      product = getIntent().getParcelableExtra(EXTRA_PRODUCT);
        createProductDetailsItem(product);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductsToCartFragment(product);
            }
        });

    }

    private void createProductDetailsItem(Product product) {
        Glide.with(this).load(product.getProductImage())
                .into(productDetailsImageView);
        productDetailsTitleTextView.setText(product.getProductTitle());
        productDetailsPriceTextView.setText(product.getProductPrice());
        increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = quantity + 1;
                quantityTextButton.setText(String.valueOf(quantity));
                cartProductQuantity = quantity;
            }
        });
        reduceQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = quantity - 1;
                quantityTextButton.setText(String.valueOf(quantity));
                cartProductQuantity = quantity;
            }
        });
    }

    private void addProductsToCartFragment(Product product) {
        cartFragment = new CartFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("cartProduct", product);
        bundle.putInt("productQuantity", cartProductQuantity);
        cartFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_activity, cartFragment)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}
