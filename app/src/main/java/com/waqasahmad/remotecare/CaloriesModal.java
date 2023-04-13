package com.waqasahmad.remotecare;

public class CaloriesModal {
    String date;

    public CaloriesModal(String date, float calories) {
        this.date = date;
        Calories = calories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getCalories() {
        return Calories;
    }

    public void setCalories(float calories) {
        Calories = calories;
    }


    float Calories;

}
