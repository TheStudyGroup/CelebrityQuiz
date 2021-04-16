package com.thestudygroup.celebrityquiz.activity;

public class UserData {

    private String id;
    private String imageURL;

    public UserData(String id,  String imageURL){
        this.id = id;
        this.imageURL = imageURL;
    }

    public UserData(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
