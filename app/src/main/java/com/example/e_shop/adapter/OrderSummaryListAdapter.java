package com.example.e_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.e_shop.R;
import com.example.e_shop.model.CartItem;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderSummaryListAdapter extends RecyclerView.Adapter<OrderSummaryListAdapter.OrderListViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<CartItem> cartItemList;


    public OrderSummaryListAdapter(Context context, ArrayList<CartItem> cartItemList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.cartItemList = cartItemList;

    }

    @NonNull
    @Override
    public OrderSummaryListAdapter.OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.ordersummary_list_item, parent,false);
        return new OrderSummaryListAdapter.OrderListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryListAdapter.OrderListViewHolder holder, int position) {
        Glide.with(context).
                load(cartItemList.get(position).getProduct().getProductImage()).
                into(holder.orderImageView);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }


    public class OrderListViewHolder extends RecyclerView.ViewHolder {
        private ImageView orderImageView;
        private TextView orderTitle;
        private TextView orderQuantityTextView;


        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            orderImageView = itemView.findViewById(R.id.order_image_view);
            orderTitle = itemView.findViewById(R.id.order_title);
            orderQuantityTextView = itemView.findViewById(R.id.order_quantity);
        }
    }
}
