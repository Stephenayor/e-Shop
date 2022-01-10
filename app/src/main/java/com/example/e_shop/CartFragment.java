package com.example.e_shop;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.e_shop.adapter.CartListAdapter;
import com.example.e_shop.adapter.ProductAdapter;
import com.example.e_shop.model.CartItem;
import com.example.e_shop.model.Product;
import com.example.e_shop.threads.Executor;
import com.example.e_shop.view.ProductDetailsActivity;
import com.example.e_shop.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment implements ProductDetailsActivity.cartButtonClickListener{
    private static final String TAG = "cartFragment";
    private Product product;
    ProductViewModel productViewModel;
    private Spinner spinner;
    List<CartItem> cartItemList = new ArrayList<>();
    private RecyclerView cartRecyclerView;
    private CartListAdapter adapter;
    public CartItem cartItem;
    public CartDatabase cartDatabase;
    private Button button;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        spinner = view.findViewById(R.id.quantity_spinner);
        cartRecyclerView = view.findViewById(R.id.cart_recycler_view);
        button = view.findViewById(R.id.cart_button);

        view.setBackgroundColor(Color.WHITE);

        productViewModel = new ProductViewModel();
        Bundle bundle = this.getArguments();
        product = bundle.getParcelable("cartProduct");
        cartItem = new CartItem(product, 1);
        cartDatabase = CartDatabase.getInstance(getContext());
//        cartDatabase.cartDao().addToCart(cartItem);
//        productViewModel.addProductToCart(product);
//        CartItem cartItem = new CartItem(product, 1);
//        cartItemList.add(cartItem);
//        displayCartList(cartItemList);
        Executor.getInstance().diskIO().execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                productViewModel.addProductToCart(cartItem);
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productViewModel.getCartItem(cartItem).observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                displayCartList(cartItems);
            }
        });
    }

    private void displayCartList(List<CartItem> cartItemList) {
        adapter = new CartListAdapter(getContext(), cartItemList);
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        cartRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickInProductDetailsActivity(View view) {
       button = view.findViewById(R.id.cart_button);
       button.setVisibility(View.INVISIBLE);
    }
}