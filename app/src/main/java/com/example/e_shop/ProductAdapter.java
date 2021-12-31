package com.example.e_shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    List<Product> productList = new ArrayList<>();


    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.productList = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent,false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Glide.with(context).
                load(productList.get(position).getProductImage()).
                into(holder.productImageView);
        holder.productTitleView.setText(productList.get(position).getProductTitle());
        holder.productPriceView.setText(productList.get(position).getProductPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImageView;
        private TextView productTitleView;
        private TextView productPriceView;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.product_image);
            productTitleView = itemView.findViewById(R.id.product_title);
            productPriceView = itemView.findViewById(R.id.product_price);
        }
    }
}
