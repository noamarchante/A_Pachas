package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.UserGroup;

public class MUserGroup {

    private long userGroupId;
    private String userGroupName;

    public MUserGroup(){

    }

    public MUserGroup(UserGroup userGroup){
        this.userGroupId = userGroup.getUserGroupId();
        this.userGroupName = userGroup.getUserGroupName();
    }

    public long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }
}
