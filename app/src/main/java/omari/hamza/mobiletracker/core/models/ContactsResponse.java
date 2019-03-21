package omari.hamza.mobiletracker.core.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContactsResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private ArrayList<FriendContacts> friendContacts;

    public ContactsResponse(boolean success, ArrayList<FriendContacts> friendContacts) {
        this.success = success;
        this.friendContacts = friendContacts;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<FriendContacts> getFriendContacts() {
        return friendContacts;
    }

    public void setFriendContacts(ArrayList<FriendContacts> friendContacts) {
        this.friendContacts = friendContacts;
    }
}
