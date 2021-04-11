package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.UserGroupUser;

public class MUserGroupUser {
    private long userGroupId;
    private long userId;

    public MUserGroupUser() {
    }

    public MUserGroupUser(UserGroupUser userGroupUser) {
        this.userGroupId = userGroupUser.getUserGroupUserId().getUserGroupId();
        this.userId = userGroupUser.getUserGroupUserId().getUserId();
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
}
