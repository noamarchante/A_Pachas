package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.UserUser;

public class MUserUser {

    private long userId;
    private long friendId;

    public MUserUser (){}

    public MUserUser (UserUser userUser){
        this.userId = userUser.getUserUserId().getUserId();
        this.friendId = userUser.getUserUserId().getFriendId();
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
}