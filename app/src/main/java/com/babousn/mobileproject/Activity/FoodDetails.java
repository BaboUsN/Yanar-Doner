package com.babousn.mobileproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.babousn.mobileproject.Activity.model.FoodItems;
import com.babousn.mobileproject.R;
import com.babousn.mobileproject.databinding.ActivityFoodDetailsBinding;
import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {
    private ActivityFoodDetailsBinding binding;
    private DataStore dataStore = DataStore.getInstance();
    private FoodItems foodItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodDetailsBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view = binding.getRoot();
        setContentView(view);
        foodItem = dataStore.getFoodItem();
        binding.popularItemName.setText(foodItem.getFoodNameAsString());
        binding.popularItemPrep.setText("Ready in "+foodItem.getPreparAsString()+" min");
        binding.popularItemPrice.setText("Price: "+foodItem.getPriceAsString()+"$");
        Picasso.get().load(foodItem.getImageUrlAsString()).into(binding.imageView);
        binding.foodContent.setText(foodItem.getContent().toString());

        binding.backtohomebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        binding.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataStore.addFoodToCart(foodItem);
                Toast.makeText(FoodDetails.this, foodItem.getFoodName()+" Added To Cart", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}