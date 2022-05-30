package com.babousn.mobileproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.babousn.mobileproject.Activity.adapter.NormalFoodRecycleAdapter;
import com.babousn.mobileproject.Activity.adapter.PopularFoodRecycleAdapter;
import com.babousn.mobileproject.Activity.model.FoodItems;
import com.babousn.mobileproject.Activity.model.TableData;
import com.babousn.mobileproject.Activity.model.UserData;
import com.babousn.mobileproject.R;
import com.babousn.mobileproject.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    PopularFoodRecycleAdapter popularFoodRecycleAdapter;
    NormalFoodRecycleAdapter normalFoodRecycleAdapter;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    ArrayList<FoodItems> popularFoodList;
    ArrayList<FoodItems> normalFoodList;
    ArrayList<FoodItems> foodItemList;
    ArrayList<TableData> tableList;
    DataStore dataStore = DataStore.getInstance();
    UserData userData;
    String gName,gid,gurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // firebase initials
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        getUser();
        //get user
       // getFoodData();

        // create arrays
        foodItemList = new ArrayList<>();
        tableList = new ArrayList<>();
        popularFoodList = new ArrayList<>();
        normalFoodList = new ArrayList<>();

        // create popular recycle
        binding.popularRecycleView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        popularFoodRecycleAdapter = new PopularFoodRecycleAdapter(popularFoodList);
        binding.popularRecycleView.setAdapter(popularFoodRecycleAdapter);

        // create normal recycle
        binding.normalRecycleView.setLayoutManager(new LinearLayoutManager(this));
        normalFoodRecycleAdapter = new NormalFoodRecycleAdapter(normalFoodList);
        binding.normalRecycleView.setAdapter(normalFoodRecycleAdapter);
        // get data from firebase
        getData();
        getFoodData();
        // binging to cart sec

        binding.cartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CartDisplay.class);
                startActivity(intent);
            }
        });
    }
public void getUser(){
    FirebaseUser currentUser = auth.getCurrentUser();
    firebaseFirestore.collection("Users")
            .whereEqualTo("useremail",currentUser.getEmail())
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot doc: task.getResult()){
                            Map<String,Object> myuserdata = doc.getData();
                            userData = new UserData(myuserdata.get("username").toString(),"@Guest",myuserdata.get("imageurl").toString());
                            dataStore.setUserData(userData);
                            gName = myuserdata.get("username").toString();
                            gurl = myuserdata.get("imageurl").toString();
                            gid = "@Guest";
                            binding.usernameText.setText(gName.toString());
                            binding.userNickNameText.setText(gid.toString());
                            // Picasso.get().load(userData.getImageUrl()).into(binding.profilepic);
                        }
                    }else{
                        System.out.println(task.getException());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    });



}
    @Override
    protected void onStart() {
        super.onStart();
        binding.cartcounter.setText(String.valueOf(dataStore.getCartData().size()));
    }

    public void getData(){
       // get food data
       /*foodItemList.add(new FoodItems("Pizza",23,90,false,"null","null"));
       foodItemList.add(new FoodItems("Burger",23,23,false,"null","null"));
       foodItemList.add(new FoodItems("Kebab",23,30,false,"null","null"));
       foodItemList.add(new FoodItems("CigKofte",23,45,false,"null","null"));
       foodItemList.add(new FoodItems("Lahmacun",23,23,false,"null","null"));
        foodItemList.add(new FoodItems("Kebab",23,30,true,"null","null"));
        foodItemList.add(new FoodItems("CigKofte",23,45,true,"null","null"));
        foodItemList.add(new FoodItems("Lahmacun",23,23,true,"null","null"));
*/
       // get table data
       Date date = new Date();
       tableList.add(new TableData(1,false,date.getTime()));
       tableList.add(new TableData(2,false,date.getTime()));
       tableList.add(new TableData(3,true,date.getTime()));
       tableList.add(new TableData(4,false,date.getTime()));
       tableList.add(new TableData(5,true,date.getTime()));
       tableList.add(new TableData(6,false,date.getTime()));
       System.out.println("-------------"+foodItemList.size());
        getFoodData();
        System.out.println("-------------"+foodItemList.size());

        //get popular and normal foods
       for(FoodItems food : foodItemList ){
           if(food.isPopular()){
               popularFoodList.add(food);
           }else{
               normalFoodList.add(food);
           }
       }
       // submit to DataStore

       dataStore.setTableList(tableList);
       dataStore.setFoodList(foodItemList);
       popularFoodRecycleAdapter.notifyDataSetChanged();
       normalFoodRecycleAdapter.notifyDataSetChanged();
   }
public void getFoodData(){
    FirebaseUser currentUser = auth.getCurrentUser();
    firebaseFirestore.collection("Foods")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot doc: task.getResult()){
                            Map<String,Object> foodDataP = doc.getData();
                            String foodNameP = (String) foodDataP.get("foodname");
                            long priceP = (long) foodDataP.get("price");
                            long prepP =(long) foodDataP.get("prep");
                            String contentP =(String) foodDataP.get("content");
                            String imageurlP = (String)foodDataP.get("imageurl");
                            boolean ispupolarP= (boolean)foodDataP.get("popular");
                            System.out.println(foodNameP+ priceP+prepP+contentP+imageurlP);
                            System.out.println(ispupolarP);
                            System.out.println(Integer.valueOf((int) priceP));
                            foodItemList.add(new FoodItems(foodNameP,Integer.valueOf((int) priceP),Integer.valueOf((int) prepP),ispupolarP,imageurlP,contentP));
                            System.out.println(foodItemList.size());

                        }
                        for(FoodItems food : foodItemList ){
                            if(food.isPopular()){
                                popularFoodList.add(food);
                            }else{
                                normalFoodList.add(food);
                            }
                        }
                        popularFoodRecycleAdapter.notifyDataSetChanged();
                        normalFoodRecycleAdapter.notifyDataSetChanged();
                    }else{
                        System.out.println(task.getException());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    });


}

}