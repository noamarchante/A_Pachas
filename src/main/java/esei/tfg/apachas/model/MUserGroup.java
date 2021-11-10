package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.UserGroup;

import java.sql.Timestamp;
import java.util.Date;

public class MUserGroup {

    private long userGroupId;
    private String userGroupName;
    private String userGroupDescription;
    private String userGroupPhoto;
    private long userGroupOwner;
    private Timestamp userGroupCreation;
    private Timestamp userGroupRemoval;

    public MUserGroup(){

    }

    public MUserGroup(UserGroup userGroup){
        this.userGroupId = userGroup.getUserGroupId();
        this.userGroupName = userGroup.getUserGroupName();
        this.userGroupDescription = userGroup.getUserGroupDescription();
        this.userGroupPhoto = userGroup.getUserGroupPhoto();
        this.userGroupCreation = userGroup.getUserGroupCreation();
        this.userGroupOwner = userGroup.getUser().getUserId();
        this.userGroupRemoval = userGroup.getUserGroupRemoval();
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

    public String getUserGroupDescription() {
        return userGroupDescription;
    }

    public void setUserGroupDescription(String userGroupDescription) {
        this.userGroupDescription = userGroupDescription;
    }

    public String getUserGroupPhoto() {
        return userGroupPhoto;
    }

    public void setUserGroupPhoto(String userGroupPhoto) {
        this.userGroupPhoto = userGroupPhoto;
    }

    public long getUserGroupOwner() {
        return userGroupOwner;
    }

    public void setUserGroupOwner(long userGroupOwner) {
        this.userGroupOwner = userGroupOwner;
    }

    public Timestamp getUserGroupRemoval() {
        return userGroupRemoval;
    }

    public void setUserGroupRemoval(Timestamp userGroupRemoval) {
        this.userGroupRemoval = userGroupRemoval;
    }

    public Timestamp getUserGroupCreation() {
        return userGroupCreation;
    }

    public void setUserGroupCreation(Timestamp userGroupCreation) {
        this.userGroupCreation = userGroupCreation;
    }
}
