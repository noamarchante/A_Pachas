package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.Group;
import java.sql.Timestamp;

public class MGroup {

    private long groupId;
    private String groupName;
    private String groupDescription;
    private String groupPhoto;
    private long groupOwner;
    private Timestamp groupCreation;
    private Timestamp groupRemoval;
    private boolean groupActive;

    public MGroup(){

    }

    public MGroup(Group group){
        this.groupId = group.getGroupId();
        this.groupName = group.getGroupName();
        this.groupDescription = group.getGroupDescription();
        this.groupPhoto = group.getGroupPhoto();
        this.groupCreation = group.getGroupCreation();
        this.groupOwner = group.getUser().getUserId();
        this.groupRemoval = group.getGroupRemoval();
        this.groupActive = group.isGroupActive();
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public long getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(long groupOwner) {
        this.groupOwner = groupOwner;
    }

    public Timestamp getGroupRemoval() {
        return groupRemoval;
    }

    public void setGroupRemoval(Timestamp groupRemoval) {
        this.groupRemoval = groupRemoval;
    }

    public Timestamp getGroupCreation() {
        return groupCreation;
    }

    public void setGroupCreation(Timestamp groupCreation) {
        this.groupCreation = groupCreation;
    }

    public boolean isGroupActive() {
        return groupActive;
    }

    public void setGroupActive(boolean groupActive) {
        this.groupActive = groupActive;
    }
}
