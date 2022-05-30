package com.babousn.mobileproject.Activity.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babousn.mobileproject.Activity.DataStore;
import com.babousn.mobileproject.Activity.FoodDetails;
import com.babousn.mobileproject.Activity.MainActivity;
import com.babousn.mobileproject.Activity.model.FoodItems;
import com.babousn.mobileproject.databinding.NormalRecycleColmBinding;
import com.babousn.mobileproject.databinding.PopularRecycleRowBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NormalFoodRecycleAdapter extends RecyclerView.Adapter<NormalFoodRecycleAdapter.NormalFoodRecycleHolder> {
    ArrayList<FoodItems> foodList;
    DataStore dataStore = DataStore.getInstance();

    public NormalFoodRecycleAdapter(ArrayList<FoodItems> foodList) {
        this.foodList = foodList;
        System.out.println("Adapter const worked");
        System.out.println(foodList.size());
    }
    @NonNull
    @Override
    public NormalFoodRecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NormalRecycleColmBinding normalRecycleColmBinding = NormalRecycleColmBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new NormalFoodRecycleHolder(normalRecycleColmBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NormalFoodRecycleHolder holder, @SuppressLint("RecyclerView") int position) {
        int pos = holder.getAdapterPosition();
        holder.binding.popularItemName.setText(foodList.get(position).getFoodNameAsString());
        holder.binding.popularItemPrep.setText("Ready in "+foodList.get(position).getPreparAsString()+" min");
        holder.binding.popularItemPrice.setText(foodList.get(position).getPriceAsString()+"$");
        Picasso.get().load(foodList.get(position).getImageUrlAsString()).into(holder.binding.popularItemImage);
        holder.binding.popularItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), FoodDetails.class);
                dataStore.setFoodItem(foodList.get(position));
                holder.itemView.getContext().startActivity(intent);

            }
        });
        holder.binding.addCartIconNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataStore.addFoodToCart(foodList.get(position));
                Toast.makeText(holder.itemView.getContext(), foodList.get(position).getFoodName()+" Added To Cart", Toast.LENGTH_SHORT).show();
            }
        });
        //holder.binding.popularItemImage.(foodList.get(position).getFoodName());
        System.out.println("Holder Worked");
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class NormalFoodRecycleHolder extends RecyclerView.ViewHolder{
        private NormalRecycleColmBinding binding;

        public NormalFoodRecycleHolder(NormalRecycleColmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
