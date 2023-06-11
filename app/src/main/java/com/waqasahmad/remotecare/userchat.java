package com.waqasahmad.remotecare;

public class userchat {

    // Declare variables
    String name, uid, onlinestatus, lastseen, p_id, emailadd;

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getEmailadd() {
        return emailadd;
    }

    public void setEmailadd(String emailadd) {
        this.emailadd = emailadd;
    }


    // Constructor with parameters
    public userchat(String name, String uid, String onlinestatus, String lastseen, String p_id) {
        this.name = name;
        this.uid = uid;
        this.onlinestatus = onlinestatus;
        this.lastseen = lastseen;
        this.p_id = p_id;
    }

    public String getLastseen() {
        return lastseen;
    }

    public void setLastseen(String lastseen) {
        this.lastseen = lastseen;
    }

    public String getOnlinestatus() {
        return onlinestatus;
    }

    public void setOnlinestatus(String onlinestatus) {
        this.onlinestatus = onlinestatus;
    }

    public userchat() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
