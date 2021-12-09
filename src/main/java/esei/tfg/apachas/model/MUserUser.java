package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.UserUser;

import java.sql.Timestamp;

public class MUserUser {

    private long userId;
    private long friendId;
    private boolean accept;

    private Timestamp userUserCreation;
    private Timestamp userUserRemoval;
    private boolean userUserActive;

    public MUserUser (){}

    public MUserUser (UserUser userUser){
        this.userId = userUser.getUserUserId().getUserId();
        this.friendId = userUser.getUserUserId().getFriendId();
        this.accept = userUser.isAccept();
        this.userUserActive = userUser.isUserUserActive();
        this.userUserCreation = userUser.getUserUserCreation();
        this.userUserRemoval = userUser.getUserUserRemoval();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    public boolean getAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public Timestamp getUserUserCreation() {
        return userUserCreation;
    }

    public void setUserUserCreation(Timestamp userUserCreation) {
        this.userUserCreation = userUserCreation;
    }

    public Timestamp getUserUserRemoval() {
        return userUserRemoval;
    }

    public void setUserUserRemoval(Timestamp userUserRemoval) {
        this.userUserRemoval = userUserRemoval;
    }

    public boolean isUserUserActive() {
        return userUserActive;
    }

    public void setUserUserActive(boolean userUserActive) {
        this.userUserActive = userUserActive;
    }
}
