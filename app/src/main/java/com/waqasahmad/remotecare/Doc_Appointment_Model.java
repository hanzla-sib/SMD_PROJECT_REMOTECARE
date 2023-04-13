package com.waqasahmad.remotecare;

public class Doc_Appointment_Model {
    String name_patient, email_patient;


    public Doc_Appointment_Model(String name_patient, String email_patient) {
        this.name_patient = name_patient;
        this.email_patient = email_patient;
    }

    public Doc_Appointment_Model() {

    }

    public String getName_patient() {
        return name_patient;
    }

    public void setName_patient(String name_patient) {
        this.name_patient = name_patient;
    }

    public String getEmail_patient() {
        return email_patient;
    }

    public void setEmail_patient(String email_patient) {
        this.email_patient = email_patient;
    }
}
