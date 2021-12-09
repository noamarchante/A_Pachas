package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.GroupUser;

import java.sql.Timestamp;

public class MGroupUser {
    private long groupId;
    private long userId;
    private Timestamp groupUserRemoval;
    private Timestamp groupUserCreation;
    private boolean groupUserActive;

    public MGroupUser() {
    }

    public MGroupUser(GroupUser groupUser) {
        this.groupId = groupUser.getGroupUserId().getGroupId();
        this.userId = groupUser.getGroupUserId().getUserId();
        this.groupUserCreation = groupUser.getGroupUserCreation();
        this.groupUserRemoval = groupUser.getGroupUserRemoval();
        this.groupUserActive = groupUser.isGroupUserActive();
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getGroupUserRemoval() {
        return groupUserRemoval;
    }

    public void setGroupUserRemoval(Timestamp groupUserRemoval) {
        this.groupUserRemoval = groupUserRemoval;
    }

    public boolean isGroupUserActive() {
        return groupUserActive;
    }

    public Timestamp getGroupUserCreation() {
        return groupUserCreation;
    }

    public void setGroupUserCreation(Timestamp groupUserCreation) {
        this.groupUserCreation = groupUserCreation;
    }

    public void setGroupUserActive(boolean groupUserActive) {
        this.groupUserActive = groupUserActive;
    }
}
