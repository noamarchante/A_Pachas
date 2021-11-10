package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.UserGroupUser;

import java.sql.Timestamp;

public class MUserGroupUser {
    private long userGroupId;
    private long userId;
    private Timestamp userGroupUserExited;

    public MUserGroupUser() {
    }

    public MUserGroupUser(UserGroupUser userGroupUser) {
        this.userGroupId = userGroupUser.getUserGroupUserId().getUserGroupId();
        this.userId = userGroupUser.getUserGroupUserId().getUserId();
        this.userGroupUserExited = userGroupUser.getUserGroupUserExited();
    }

    public long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getUserGroupUserExited() {
        return userGroupUserExited;
    }

    public void setUserGroupUserExited(Timestamp userGroupUserExited) {
        this.userGroupUserExited = userGroupUserExited;
    }
}
