package com.waqasahmad.remotecare;

// Model class for the steps to record the date and the number of steps
public class Steps_Modal {
    String date;
    int steps;

    public Steps_Modal(String date, int steps) {
        this.date = date;
        this.steps = steps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
