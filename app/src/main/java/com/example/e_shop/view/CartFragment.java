package com.example.e_shop.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.e_shop.R;
import com.example.e_shop.adapter.CartListAdapter;
import com.example.e_shop.database.CartDatabase;
import com.example.e_shop.model.CartItem;
import com.example.e_shop.model.Product;
import com.example.e_shop.threads.Executor;
import com.example.e_shop.view.OrderSummaryActivity;
import com.example.e_shop.viewmodel.ProductViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment implements CartListAdapter.CartInterface {
    private static final String EXTRA_PRODUCT_QUANTITY = "productQuantity";
    private Product product;
    ProductViewModel productViewModel;
    ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();
    private RecyclerView cartRecyclerView;
    private CartListAdapter adapter;
    public CartItem cartItem;
    public CartDatabase cartDatabase;
    private int cartProductQuantity;
    private Button placeOrderButton;
    private TextView cartItemTotalPriceTextView;

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
        cartRecyclerView = view.findViewById(R.id.cart_recycler_view);
        placeOrderButton = view.findViewById(R.id.place_order_Button);
        cartItemTotalPriceTextView = view.findViewById(R.id.cartTotalTextView);
        view.setBackgroundColor(Color.WHITE);

        productViewModel = new ProductViewModel();
        Bundle bundle = this.getArguments();
        product = bundle.getParcelable("cartProduct");
        cartProductQuantity = bundle.getInt(EXTRA_PRODUCT_QUANTITY);
        cartItem = new CartItem(product, cartProductQuantity);
        cartDatabase = CartDatabase.getInstance(getContext());
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
        Snackbar.make(requireView(), cartItem.getProduct().getProductTitle()
                + "Added to cart.", Snackbar.LENGTH_LONG).show();
        productViewModel.getCartItem(cartItem).observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                displayCartList(cartItems);
                placeOrderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startOrderSummaryActivity(cartItems);
                    }
                });
                calculateCartItemTotalPrice(cartItems);
            }
        });
    }

    private void calculateCartItemTotalPrice(List<CartItem> cartItemList) {
        productViewModel.getTotalPrice(cartItemList).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                cartItemTotalPriceTextView.setText("Total: $ " + aDouble.toString());
            }
        });
    }

    private void displayCartList(List<CartItem> cartItemList) {
        adapter = new CartListAdapter(getContext(), cartItemList, this);
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        cartRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NotNull final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Executor.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        productViewModel.removeProductFromCart(cartItemList.get(position));
                    }
                });
            }
        }).attachToRecyclerView(cartRecyclerView);
    }

    private void startOrderSummaryActivity(List<CartItem> orderCartItem) {
        cartItemList.addAll(orderCartItem);
        Intent intent = new Intent(getActivity(), OrderSummaryActivity.class);
        intent.putParcelableArrayListExtra("orderList", cartItemList);
        startActivity(intent);
    }

    @Override
    public void changeQuantity(CartItem cartItem, int quantity) {
    }
}