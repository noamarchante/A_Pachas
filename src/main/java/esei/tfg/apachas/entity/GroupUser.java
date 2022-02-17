package esei.tfg.apachas.entity;

import esei.tfg.apachas.entity.id.GroupUserId;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "groupUser")
@Entity(name = "groupUser")
public class GroupUser implements Serializable {

    @EmbeddedId
    private GroupUserId groupUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    @JoinColumn(name = "groupId", referencedColumnName = "groupId", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Column(name = "groupUserCreation")
    private Timestamp groupUserCreation;

    @Column(name = "groupUserRemoval")
    private Timestamp groupUserRemoval;

    @Column(name = "groupUserActive", length = 1)
    @Size(min = 1, max = 1)
    private boolean groupUserActive;

    public GroupUser() {

    }

    public GroupUser(GroupUserId groupUserId) {
        this.groupUserId = groupUserId;
        this.setGroupUserCreation(new Timestamp(System.currentTimeMillis()));
        this.setGroupUserRemoval(null);
        this.setGroupUserActive(true);
    }

    public GroupUserId getGroupUserId() {
        return groupUserId;
    }

    public void setGroupUserId(GroupUserId groupUserId) {
        this.groupUserId = groupUserId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getGroupUserCreation() {
        return groupUserCreation;
    }

    public void setGroupUserCreation(Timestamp groupUserCreation) {
        this.groupUserCreation = groupUserCreation;
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

    public void setGroupUserActive(boolean groupUserActive) {
        this.groupUserActive = groupUserActive;
    }
}
