package com.babousn.mobileproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.babousn.mobileproject.Activity.adapter.CartRecycleAdapter;
import com.babousn.mobileproject.Activity.adapter.NormalFoodRecycleAdapter;
import com.babousn.mobileproject.Activity.model.FoodItems;
import com.babousn.mobileproject.R;
import com.babousn.mobileproject.databinding.ActivityCartDisplayBinding;

import java.util.ArrayList;

public class CartDisplay extends AppCompatActivity {
    private ActivityCartDisplayBinding binding;
    private CartRecycleAdapter cartRecycleAdapter;
    private ArrayList<FoodItems> cartList;
    DataStore dataStore = DataStore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartDisplayBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view = binding.getRoot();
        setContentView(view);
        cartList = dataStore.getCartData();
        binding.cartDisplayRecycle.setLayoutManager(new LinearLayoutManager(this));
        cartRecycleAdapter = new CartRecycleAdapter(cartList);
        binding.cartDisplayRecycle.setAdapter(cartRecycleAdapter);
        binding.applyCartAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BookedSec.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cartRecycleAdapter.notifyDataSetChanged();
    }
}