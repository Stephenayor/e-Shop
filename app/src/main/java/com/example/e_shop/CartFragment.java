package com.example.e_shop;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.e_shop.view.ProductDetailsActivity;
import com.example.e_shop.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {
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
        cartDatabase = CartDatabase.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        spinner = view.findViewById(R.id.quantity_spinner);
        cartRecyclerView = view.findViewById(R.id.cart_recycler_view);
        button = view.findViewById(R.id.cart_button);
        button.setVisibility(View.INVISIBLE);
        view.setBackgroundColor(Color.WHITE);

        productViewModel = new ProductViewModel();
        Bundle bundle = this.getArguments();
        product = bundle.getParcelable("cartProduct");
        cartItem = new CartItem(product, 1);
        cartDatabase.cartDao().addToCart(cartItem);
//        productViewModel.addProductToCart(product);
//        CartItem cartItem = new CartItem(product, 1);
//        cartItemList.add(cartItem);
//        displayCartList(cartItemList);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productViewModel.getCartItem(cartItem).observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
            }
        });
        cartItemList = cartDatabase.cartDao().getCartList();
        displayCartList(cartItemList);
    }

    private void displayCartList(List<CartItem> cartItemList) {
        adapter = new CartListAdapter(getContext(), cartItemList);
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        cartRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}