package com.waqasahmad.remotecare;

public class HeartBeat_modal {
    String date;
    float HR;

    public HeartBeat_modal(String date, float HR) {
        this.date = date;
        this.HR = HR;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getHR() {
        return HR;
    }

    public void setHR(float HR) {
        this.HR = HR;
    }

}
