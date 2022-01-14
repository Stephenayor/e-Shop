package com.example.e_shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.e_shop.adapter.CartListAdapter;
import com.example.e_shop.adapter.OrderSummaryListAdapter;
import com.example.e_shop.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity {
    ArrayList<CartItem> orderSummaryList;
    private RecyclerView orderRecyclerView;
    private OrderSummaryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
    orderRecyclerView = findViewById(R.id.order_summary_recyclerView);
    orderSummaryList = getIntent().getParcelableArrayListExtra("orderList");
    displayOrderSummaryList(orderSummaryList);
    }

    private void displayOrderSummaryList(ArrayList<CartItem> summaryList) {
        adapter = new OrderSummaryListAdapter(this, summaryList);
        orderRecyclerView.setHasFixedSize(true);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        orderRecyclerView.setAdapter(adapter);
    }
}