package com.waqasahmad.remotecare;

public class Profile_signup {

    public String name, email, pass, gender, dp;

    public Profile_signup(String name, String email, String pass, String gender)//,String dp)
    {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.pass = pass;
    }


    public Profile_signup() {
    }

    public Profile_signup(String dp) {
        this.dp = dp;
    }

    public String getDp() {
        return dp;
    }

    public String getName() {
        return name;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

}



