package com.example.fragmentlogin.model;

public class DataClass {
    String fname;
    String lname;
    String city;
    String about;

    String uid;
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public DataClass(String fname, String lname, String city, String about) {
        this.fname = fname;
        this.lname = lname;
        this.city = city;
        this.about = about;
    }

    public DataClass() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
