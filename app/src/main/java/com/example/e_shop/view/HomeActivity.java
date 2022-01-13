package com.example.e_shop.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.e_shop.CartFragment;
import com.example.e_shop.model.Product;
import com.example.e_shop.adapter.ProductAdapter;
import com.example.e_shop.R;
import com.example.e_shop.viewmodel.ProductItemViewmodel;
import com.example.e_shop.viewmodel.ProductViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.e_shop.R.id.action_cart_icon;
import static com.example.e_shop.R.id.position;

public class HomeActivity extends AppCompatActivity implements ProductAdapter.ItemClickListener {
    List<Product> productList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ProductViewModel productViewModel;
    private ProductItemViewmodel productItemViewmodel;
    private Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.product_recyclerView);

        productViewModel = new ProductViewModel();
        productViewModel.getProducts().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                productList.add(product);
                displayProducts();
            }
        });}

    private void displayProducts() {
        adapter = new ProductAdapter(getBaseContext(), productList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onProductItemClick(Product product) {
        Intent productDetailsIntent = new Intent(this, ProductDetailsActivity.class);
        productItemViewmodel = new ProductItemViewmodel();
        productItemViewmodel.selectItem(product);
        productDetailsIntent.putExtra("productItem", product);
        startActivity(productDetailsIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case action_cart_icon:
               Fragment cartFragment = new CartFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_activity, cartFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void openCartFragment(View view) {

    }
}