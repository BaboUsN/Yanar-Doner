package com.babousn.mobileproject.Activity;

import com.babousn.mobileproject.Activity.model.FoodItems;
import com.babousn.mobileproject.Activity.model.TableData;
import com.babousn.mobileproject.Activity.model.UserData;

import java.util.ArrayList;

public class DataStore {
    private FoodItems foodItem;
    private ArrayList<FoodItems> foodList = new ArrayList<>();
    private ArrayList<TableData> tableList = new ArrayList<>();
    private ArrayList<FoodItems> cartData = new ArrayList<>();
    private int minPrepTime;
    private UserData userData;
    private static DataStore dataStore;
    private int currentTable;
    private DataStore(){

    }

    // current table

    public int getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(int currentTable) {
        this.currentTable = currentTable;
    }
// cart data

    public ArrayList<FoodItems> getCartData() {
        return cartData;
    }

    public void setCartData(ArrayList<FoodItems> cartData) {
        this.cartData = cartData;
    }

    public void addFoodToCart(FoodItems foodItem){
        this.cartData.add(foodItem);
    }
    public void deleteFoodFromCart(int index){
        this.cartData.remove(index);
    }

    //user data

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    // Food Item

    public FoodItems getFoodItem() {
        if(this.foodItem == null){
            return new FoodItems("Pizza",23,21,false,"error","null");
        }
        return foodItem;
    }

    // Firebase funs

    public boolean updateFirebase(){
        return false;
    }

    public void setFoodItem(FoodItems foodItem) {
        this.foodItem = foodItem;
    }

    // Food List arrangement

    public void setFoodList(ArrayList<FoodItems> foodList) {
        this.foodList = foodList;
    }

    public void addFoodToList(FoodItems foodItem){
        this.foodList.add(foodItem);
    }

    public ArrayList<FoodItems> getFoodList(){
        return this.foodList;
    }

    // Table list arrangement

    public ArrayList<TableData> getTableList() {
        return tableList;
    }

    public void setTableList(ArrayList<TableData> tableList) {
        this.tableList = tableList;
    }

    // minprep time

    public int getMinPrepTime() {
        return minPrepTime;
    }

    public void setMinPrepTime(int minPrepTime) {
        this.minPrepTime = minPrepTime;
    }

    // Singleton usage

    public static DataStore getInstance() {
        if (dataStore == null) {
            dataStore = new DataStore();
        }
        return dataStore;

    }

}
