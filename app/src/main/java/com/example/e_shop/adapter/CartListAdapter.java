package com.example.e_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_shop.R;
import com.example.e_shop.model.CartItem;
import com.example.e_shop.model.Product;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<CartItem> cartItemList = new ArrayList<>();

    public CartListAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent,false);
        return new CartListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
        Glide.with(context).
                load(cartItemList.get(position).getProduct().getProductImage()).
                into(holder.productCartImageView);
        holder.productCartTitleTextView.setText(cartItemList.get(position).getProduct().getProductTitle());

    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class CartListViewHolder extends RecyclerView.ViewHolder {
        private ImageView productCartImageView;
        private TextView productCartTitleTextView;
        private TextView productCartQuantityTextView;
        private Spinner spinner;
        private Button deleteButton;
        private TextView totalPriceTextView;
        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);
            productCartImageView = itemView.findViewById(R.id.productImageView);
            productCartTitleTextView = itemView.findViewById(R.id.productTitleTextView);
            productCartQuantityTextView = itemView.findViewById(R.id.quanity_textView);
            spinner = itemView.findViewById(R.id.quantity_spinner);

            totalPriceTextView = itemView.findViewById(R.id.product_totalPrice_textView);
        }
    }
}
