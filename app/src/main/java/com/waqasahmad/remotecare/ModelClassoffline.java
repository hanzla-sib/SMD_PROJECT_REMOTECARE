package com.waqasahmad.remotecare;

import android.graphics.Bitmap;

public class ModelClassoffline {
   private String email;
   private Bitmap image;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public ModelClassoffline(String email, Bitmap image) {
        this.email = email;
        this.image = image;
    }
}
