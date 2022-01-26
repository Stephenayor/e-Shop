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

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<CartItem> cartItemList = new ArrayList<>();
    private CartInterface cartInterface;

    public CartListAdapter(Context context, List<CartItem> cartItemList, CartInterface cartInterface) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.cartItemList = cartItemList;
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item,
                parent, false);
        return new CartListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
        Glide.with(context).
                load(cartItemList.get(position).getProduct().getProductImage()).
                into(holder.productCartImageView);
        holder.productCartTitleTextView.setText(cartItemList.get(position).getProduct().getProductTitle());
        holder.cartQuantityTextView.setText(Integer.toString(cartItemList.get(position).getQuantity()));
        int cartQuantity = cartItemList.get(position).getQuantity();
        String testCart = cartItemList.get(position).getProduct().getProductPrice();
        String str = testCart.substring(1);
        int cartProductPrice = Integer.parseInt(str);
        holder.productCartPriceTextView.setText(String.valueOf("$" + cartProductPrice * cartQuantity));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public class CartListViewHolder extends RecyclerView.ViewHolder {
        private ImageView productCartImageView;
        private TextView productCartTitleTextView;
        private TextView productCartPriceTextView;
        private TextView productCartQuantityTextView;
        private TextView cartQuantityTextView;
        private Spinner spinner;

        public CartListViewHolder(@NonNull View itemView) {
            super(itemView);
            productCartImageView = itemView.findViewById(R.id.productImageView);
            productCartTitleTextView = itemView.findViewById(R.id.productTitleTextView);
            productCartQuantityTextView = itemView.findViewById(R.id.quantity_textView);
            productCartPriceTextView = itemView.findViewById(R.id.cart_item_price_textView);
            cartQuantityTextView = itemView.findViewById(R.id.cartQuantity_text_View);
        }
    }

    public interface CartInterface {
        void changeQuantity(CartItem cartItem, int quantity);
    }
}
