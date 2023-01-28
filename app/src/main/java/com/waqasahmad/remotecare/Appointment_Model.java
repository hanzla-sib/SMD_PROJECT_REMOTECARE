package com.waqasahmad.remotecare;


public class Appointment_Model {

    String name_doc,email_doc,image_doc, time_doc, date_doc;


    public String getImage_doc() {
        return image_doc;
    }

    public void setImage_doc(String image_doc) {
        this.image_doc = image_doc;
    }



    public Appointment_Model(String name_doc, String email_doc, String image_doc, String doc_date, String doc_time)
    {
        this.name_doc = name_doc;
        this.email_doc = email_doc;
        this.image_doc=image_doc;

        this.time_doc = doc_time;
        this.date_doc = doc_date;


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

    public String getTime_doc() {
        return time_doc;
    }

    public void setTime_doc(String time_doc) {
        this.time_doc = time_doc;
    }

    public String getDate_doc() {
        return date_doc;
    }

    public void setDate_doc(String date_doc) {
        this.date_doc = date_doc;
    }
}
