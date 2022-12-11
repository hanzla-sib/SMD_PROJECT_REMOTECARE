package com.waqasahmad.remotecare;
public class createuserinrealtime {


    public String name,email,pass,gender,dp,uid,type,date,time,status,player_id;

    public createuserinrealtime (String name, String email, String pass,String gender,String uid,String type,String date,String time,String status,String player_id)
    {
        this.name=name;
        this.email=email;
        this.gender=gender;
        this.pass=pass;
        this.uid=uid;
        this.type=type;
        this.date=date;
        this.time=time;
        this.status=status;
        this.player_id=player_id;

    }


    public createuserinrealtime(){}
    public createuserinrealtime(String dp){
        this.dp=dp;
    }




    public String getDp() {
        return dp;
    }

    public String getName() {
        return name;
    }


    public String getuid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setuid(String uid) {
        this.email = uid;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setDp(String dp) { this.dp = dp; }

    public void settype(String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }


}
