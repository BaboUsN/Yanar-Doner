package com.babousn.mobileproject.Activity.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babousn.mobileproject.Activity.DataStore;
import com.babousn.mobileproject.Activity.model.FoodItems;
import com.babousn.mobileproject.databinding.CartRecycleviewRowBinding;

import java.util.ArrayList;

public class CartRecycleAdapter extends RecyclerView.Adapter<CartRecycleAdapter.CartRecycleHolder> {
    DataStore dataStore = DataStore.getInstance();
    ArrayList<FoodItems> foodList;

    public CartRecycleAdapter(ArrayList<FoodItems> foodList){
        this.foodList = foodList;
    }
    @NonNull
    @Override
    public CartRecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartRecycleviewRowBinding cartRecycleviewRowBinding = CartRecycleviewRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CartRecycleHolder(cartRecycleviewRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecycleHolder holder, @SuppressLint("RecyclerView") int position) {
        foodList = dataStore.getCartData();
        holder.binding.popularItemName.setText(foodList.get(position).getFoodNameAsString());
        holder.binding.popularItemPrice.setText(foodList.get(position).getPriceAsString()+"$");
        holder.binding.deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemRemoved(position);
                foodList.remove(position);
                dataStore.deleteFoodFromCart(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class CartRecycleHolder extends RecyclerView.ViewHolder {
        private CartRecycleviewRowBinding binding;
        public CartRecycleHolder(CartRecycleviewRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
