package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.UserGroupUser;

import java.util.Date;

public class MUserGroupUser {
    private long userGroupId;
    private long userId;
    private Date userGroupUserExited;

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

    public Date getUserGroupUserExited() {
        return userGroupUserExited;
    }

    public void setUserGroupUserExited(Date userGroupUserExited) {
        this.userGroupUserExited = userGroupUserExited;
    }
}
