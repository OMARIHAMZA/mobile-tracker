package omari.hamza.mobiletracker.core.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("access_token")
    private String token;


    @SerializedName("user")
    private User user;

    @SerializedName("data")
    private ArrayList<Contact> contacts;



    public MyResponse(boolean success, String token, User user, ArrayList<Contact> contacts) {
        this.success = success;
        this.token = token;
        this.user = user;
        this.contacts = contacts;
    }


    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
