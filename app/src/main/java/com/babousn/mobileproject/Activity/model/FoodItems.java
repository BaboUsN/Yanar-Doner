package com.babousn.mobileproject.Activity.model;

public class FoodItems {
    private String foodName,imageUrl,content;
    private int preparation,price;
    boolean isPopular;

    public FoodItems(String foodName,int price, int preparation ,boolean isPopular, String imageUrl,String content) {
        this.foodName = foodName;
        this.imageUrl = imageUrl;
        this.preparation = preparation;
        this.price = price;
        this.content = content;
        this.isPopular = isPopular;
    }

    public boolean isPopular() {
        return isPopular;
    }

    public void setPopular(boolean popular) {
        isPopular = popular;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPreparation() {
        return preparation;
    }

    public int getPrice() {
        return price;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPreparation(int preparation) {
        this.preparation = preparation;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public String getFoodNameAsString(){
        return String.valueOf(this.foodName);
    }
    public String getPriceAsString(){
        return String.valueOf(this.price);
    }
    public String getPreparAsString(){
        return String.valueOf(this.preparation);
    }
    public String getImageUrlAsString(){
        return String.valueOf(this.imageUrl);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
