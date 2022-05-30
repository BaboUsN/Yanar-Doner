package com.babousn.mobileproject.Activity.model;

public class UserData {
    private String name,imageUrl,userid;

    public UserData(String name, String userid,String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
