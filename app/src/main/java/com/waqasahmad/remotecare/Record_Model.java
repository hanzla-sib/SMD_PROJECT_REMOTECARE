package com.waqasahmad.remotecare;

public class Record_Model {
    String image_url;
    String details;
    String Record_num;


    public String getRecord_num() {
        return Record_num;
    }

    public void setRecord_num(String record_num) {
        Record_num = record_num;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
