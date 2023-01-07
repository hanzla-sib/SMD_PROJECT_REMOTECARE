package com.waqasahmad.remotecare;


public class Appointment_Model {

    String name_doc,email_doc,image_doc;


    public String getImage_doc() {
        return image_doc;
    }

    public void setImage_doc(String image_doc) {
        this.image_doc = image_doc;
    }

    public Appointment_Model(String name_doc, String email_doc, String image_doc)
    {
        this.name_doc = name_doc;
        this.email_doc = email_doc;
        this.image_doc=image_doc;

    }
    public Appointment_Model()
    {

    }

    public String getName_doc() {
        return name_doc;
    }

    public void setName_doc(String name_doc) {
        this.name_doc = name_doc;
    }

    public String getEmail_doc() {
        return email_doc;
    }

    public void setEmail_doc(String email_doc) {
        this.email_doc = email_doc;
    }
}
