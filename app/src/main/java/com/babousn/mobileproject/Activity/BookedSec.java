package com.babousn.mobileproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.babousn.mobileproject.Activity.model.FoodItems;
import com.babousn.mobileproject.Activity.model.TableData;
import com.babousn.mobileproject.R;
import com.babousn.mobileproject.databinding.ActivityBookedSecBinding;

import java.util.ArrayList;

public class BookedSec extends AppCompatActivity {
    private ActivityBookedSecBinding binding;
    private DataStore dataStore = DataStore.getInstance();
    private ArrayList<TableData> tableList;
    int tableindex = -99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookedSecBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        tableList = dataStore.getTableList();
        refleshTables(-1);
        binding.complateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(tableindex == -99)){
                    calculateAllActions();
                }else{
                    Toast.makeText(BookedSec.this, "You have to select a Table", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void tableClicked(View view){
        switch (view.getId()){
            case R.id.table1:
                if(checkBoooked(1)){
                    Toast.makeText(getApplicationContext(), "You cannot select booked Tables", Toast.LENGTH_SHORT).show();
                }else{
                    binding.table1.setImageResource(R.drawable.tableselected);
                    refleshTables(1);
                    tableindex = 1;
                }

                break;
            case R.id.table2:
                if(checkBoooked(2)){
                    Toast.makeText(getApplicationContext(), "You cannot select booked Tables", Toast.LENGTH_SHORT).show();
                }else{
                    binding.table2.setImageResource(R.drawable.tableselected);
                    refleshTables(2);
                    tableindex = 2;
                }

                break;
            case R.id.table3:
                if(checkBoooked(3)){
                    Toast.makeText(getApplicationContext(), "You cannot select booked Tables", Toast.LENGTH_SHORT).show();
                }else{
                    binding.table3.setImageResource(R.drawable.tableselected);
                    refleshTables(3);
                    tableindex = 3;
                }

                break;
            case R.id.table4:
                if(checkBoooked(4)){
                    Toast.makeText(getApplicationContext(), "You cannot select booked Tables", Toast.LENGTH_SHORT).show();
                }else{
                    binding.table4.setImageResource(R.drawable.tableselected);
                    refleshTables(4);
                    tableindex = 4;
                }

                break;
            case R.id.table5:
                if(checkBoooked(5)){
                    Toast.makeText(getApplicationContext(), "You cannot select booked Tables", Toast.LENGTH_SHORT).show();
                }else{
                    binding.table5.setImageResource(R.drawable.tableselected);
                    refleshTables(5);
                    tableindex = 5;
                }

                break;
            case R.id.table6:
                if(checkBoooked(6)){
                    Toast.makeText(getApplicationContext(), "You cannot select booked Tables", Toast.LENGTH_SHORT).show();
                }else{
                    binding.table6.setImageResource(R.drawable.tableselected);
                    refleshTables(6);
                    tableindex = 6;
                }

                break;

        }
    }
    public boolean checkBoooked(int index){
        for(TableData table : tableList){
            if(table.getTableNum() == index){
                return table.isBooked();
            }
        }
        return false;
    }
    public void setTableBooked(int index){
        for(TableData table : tableList){
            if(table.getTableNum() == index){
                table.setBooked(true);
                dataStore.setTableList(tableList);
                dataStore.updateFirebase();
                dataStore.setCurrentTable(index);
            }
        }
    }
    public void refleshTables(int x){
        for(TableData table: tableList){
            boolean status = table.isBooked();
            int num = table.getTableNum();
            if (num == x){
                continue;
            }
        switch (num){
        case 1:
            if(status){
                binding.table1.setImageResource(R.drawable.tablebooked);
            }else{
                binding.table1.setImageResource(R.drawable.tablefree);
            }
            break;
        case 2:
            if(status){
                binding.table2.setImageResource(R.drawable.tablebooked);
            }else{
                binding.table2.setImageResource(R.drawable.tablefree);
            }
            break;
        case 3:
            if(status){
                binding.table3.setImageResource(R.drawable.tablebooked);
            }else{
                binding.table3.setImageResource(R.drawable.tablefree);
            }
            break;
        case 4:
            if(status){
                binding.table4.setImageResource(R.drawable.tablebooked);
            }else{
                binding.table4.setImageResource(R.drawable.tablefree);
            }
            break;
        case 5:
            if(status){
                binding.table5.setImageResource(R.drawable.tablebooked);
            }else{
                binding.table5.setImageResource(R.drawable.tablefree);
            }
            break;
        case 6:
            if(status){
                binding.table6.setImageResource(R.drawable.tablebooked);
            }else{
                binding.table6.setImageResource(R.drawable.tablefree);
            }
            break;
        }
    }
}
public void calculateAllActions(){
        setTableBooked(tableindex);
        ArrayList<FoodItems> foodList= dataStore.getCartData();
        int minPrep = -9999;
        for (FoodItems food : foodList){
            if(food.getPreparation() > minPrep){
                minPrep = food.getPreparation();
            }
        }
        dataStore.setMinPrepTime(minPrep);
    Intent intent = new Intent(getApplicationContext(),PendingSec.class);
    startActivity(intent);
}
}