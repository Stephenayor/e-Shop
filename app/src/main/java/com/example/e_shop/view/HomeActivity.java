package com.example.e_shop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.e_shop.model.Product;
import com.example.e_shop.adapter.ProductAdapter;
import com.example.e_shop.R;
import com.example.e_shop.viewmodel.ProductItemViewmodel;
import com.example.e_shop.viewmodel.ProductViewModel;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ProductAdapter.ItemClickListener {
    List<Product> productList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ProductViewModel productViewModel;
    private ProductItemViewmodel productItemViewmodel;
    private Product product;
    private ImageView cartImageView;
    private ProgressDialog progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.product_recyclerView);
        cartImageView = findViewById(R.id.cart_icon);
        progressBar = new ProgressDialog(this);
        progressBar.show();
        progressBar.setMessage("PRODUCTS ARE LOADING ...");
        progressBar.setCancelable(true);
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        productViewModel = new ProductViewModel();
        productViewModel.getProducts().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                productList.add(product);
                displayProducts();
            }
        });
    }

    private void displayProducts() {
        adapter = new ProductAdapter(getBaseContext(), productList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        progressBar.dismiss();
    }

    @Override
    public void onProductItemClick(Product product) {
        Intent productDetailsIntent = new Intent(this, ProductDetailsActivity.class);
        productItemViewmodel = new ProductItemViewmodel();
        productItemViewmodel.selectItem(product);
        productDetailsIntent.putExtra("productItem", product);
        startActivity(productDetailsIntent);
    }

    public void openCartFragment(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.cart_fragment, CartFragment.class, null)
                .commit();
    }
}


