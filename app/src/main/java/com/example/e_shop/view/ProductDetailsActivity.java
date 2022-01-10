package com.example.e_shop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_shop.CartFragment;
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
    public cartButtonClickListener cartButtonClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
      productDetailsImageView = findViewById(R.id.product_details_imageView);
      productDetailsTitleTextView = findViewById(R.id.product_details_titleView);
      productDetailsPriceTextView = findViewById(R.id.product_details_priceView);
      addToCartButton = findViewById(R.id.cart_button);

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
    }

    private void addProductsToCartFragment(Product product) {
        cartFragment = new CartFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("cartProduct", product);
        cartFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_activity, cartFragment)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public interface cartButtonClickListener{
        void onClickInProductDetailsActivity(View view);
    }

}
