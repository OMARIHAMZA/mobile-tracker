package omari.hamza.mobiletracker.core.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contact implements Serializable {

    @SerializedName("user_id")
    private int id;

    @SerializedName("friendship_id")
    private int friendshipId;

    @SerializedName("request_id")
    private int connectionRequestId;

    @SerializedName("username")
    private String username;

    @SerializedName("phone")
    private String phone;

    @SerializedName("brand")
    private String phoneBrand;

    @SerializedName("phone_model")
    private String phoneModel;

    @SerializedName("long")
    private String longitude;

    @SerializedName("lat")
    private String latitude;

    public Contact(int id, int friendshipId, int connectionRequestId, String username, String phone, String phoneBrand, String phoneModel, String longitude, String latitude) {
        this.id = id;
        this.friendshipId = friendshipId;
        this.connectionRequestId = connectionRequestId;
        this.username = username;
        this.phone = phone;
        this.phoneBrand = phoneBrand;
        this.phoneModel = phoneModel;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getConnectionRequestId() {
        return connectionRequestId;
    }

    public void setConnectionRequestId(int connectionRequestId) {
        this.connectionRequestId = connectionRequestId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(int friendshipId) {
        this.friendshipId = friendshipId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
