package com.waqasahmad.remotecare;

public class MyModel {

    String name, calorie;

    // Model for the food name and its calories
    public MyModel(String name, String calorie) {
        this.name = name;
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }
}
