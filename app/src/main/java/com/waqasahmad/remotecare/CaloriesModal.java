package com.waqasahmad.remotecare;
public class CaloriesModal
{
    String date;

    public CaloriesModal(String date, int calories)
    {
        this.date = date;
        Calories = calories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }

    int Calories;

}
