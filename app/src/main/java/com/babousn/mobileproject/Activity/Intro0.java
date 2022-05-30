package com.babousn.mobileproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.babousn.mobileproject.R;
import com.babousn.mobileproject.databinding.ActivityIntro0Binding;

public class Intro0 extends AppCompatActivity {
    private ActivityIntro0Binding binding;
    Animation topAnim,bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntro0Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);
        // Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        binding.donerName.setAnimation(bottomAnim);
        binding.donerSubName.setAnimation(bottomAnim);
        binding.imageView.setAnimation(topAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Intro1.class));
                overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);
            }
        },5000);
    }
}