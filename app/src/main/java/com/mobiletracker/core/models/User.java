package com.mobiletracker.core.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    private String name;

    @SerializedName("phone")
    private String mobile;

    public User(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
