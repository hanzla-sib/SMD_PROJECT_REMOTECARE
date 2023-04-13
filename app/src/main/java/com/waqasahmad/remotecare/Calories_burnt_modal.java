package com.waqasahmad.remotecare;

public class Calories_burnt_modal {
    String date;
    float calories_burnt;

    public Calories_burnt_modal(String date, float calories_burnt) {
        this.date = date;
        this.calories_burnt = calories_burnt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getCalories_burnt() {
        return calories_burnt;
    }

    public void setCalories_burnt(float calories_burnt) {
        this.calories_burnt = calories_burnt;
    }

}
