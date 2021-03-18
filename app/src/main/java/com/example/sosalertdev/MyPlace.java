package com.example.sosalertdev;

import android.net.Uri;

import com.google.android.libraries.places.api.model.OpeningHours;

public class MyPlace {

    private String id;
    private String name;
    private String address;
    private String phoneNum;
    private OpeningHours hours;
    private Uri website;


    public MyPlace(String id, String name, String address, String phoneNum, OpeningHours hours, Uri website) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.hours = hours;
        this.website = website;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public OpeningHours getHours() {
        return hours;
    }

    public void setHours(OpeningHours hours) {
        this.hours = hours;
    }

    public Uri getWebsite() {
        return website;
    }

    public void setWebsite(Uri website) {
        this.website = website;
    }
}
