package com.babousn.mobileproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.babousn.mobileproject.Activity.model.UserData;
import com.babousn.mobileproject.R;
import com.babousn.mobileproject.databinding.ActivityPendingSecBinding;

import java.util.ArrayList;

public class PendingSec extends AppCompatActivity {
    private ActivityPendingSecBinding binding;
    private DataStore dataStore = DataStore.getInstance();
    int minPrep = dataStore.getMinPrepTime();
    UserData userData = dataStore.getUserData();
    String contentMessage;
    int currentTable = dataStore.getCurrentTable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityPendingSecBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        contentMessage = "We are preparing your table for you. "+"You can sit "
                + currentTable+". table when its prepared. \n\nEstimated preparation time is "+(minPrep+5)+"min";
        binding.message.setText(contentMessage);
        // binding.name.setText("Hello "+ userData.getName()+"!");
    }
}