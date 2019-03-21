package omari.hamza.mobiletracker.core.models;

import com.google.gson.annotations.SerializedName;

public class FriendContacts {

    @SerializedName("username")
    private String friendName;

    @SerializedName("id")
    private int friendId;

    @SerializedName("contacts")
    private String contacts;

    public FriendContacts(String friendName, int friendId, String contacts) {
        this.friendName = friendName;
        this.friendId = friendId;
        this.contacts = contacts;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
