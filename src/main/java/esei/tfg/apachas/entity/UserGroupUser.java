package esei.tfg.apachas.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import esei.tfg.apachas.entity.id.UserGroupUserId;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Table(name = "UserGroupUser")
@Entity(name = "UserGroupUser")
public class UserGroupUser implements Serializable {

    @EmbeddedId
    private UserGroupUserId userGroupUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userGroupId")
    @JoinColumn(name = "userGroupId", referencedColumnName = "userGroupId", nullable = false)
    private UserGroup userGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Column(name = "userGroupUserAdded")
    private Timestamp userGroupUserAdded;

    @Column(name = "userGroupUserExited")
    private Timestamp userGroupUserExited;

    public UserGroupUser() {

    }

    public UserGroupUser(UserGroupUserId userGroupUserId, Timestamp userGroupUserExited) {
        this.userGroupUserId = userGroupUserId;
        this.userGroupUserAdded = new Timestamp(new Date().getTime());
        this.userGroupUserExited = userGroupUserExited;
    }

    public UserGroupUserId getUserGroupUserId() {
        return userGroupUserId;
    }

    public void setUserGroupUserId(UserGroupUserId userGroupUserId) {
        this.userGroupUserId = userGroupUserId;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getUserGroupUserAdded() {
        return userGroupUserAdded;
    }

    public void setUserGroupUserAdded(Timestamp userGroupUserAdded) {
        this.userGroupUserAdded = userGroupUserAdded;
    }

    public Timestamp getUserGroupUserExited() {
        return userGroupUserExited;
    }

    public void setUserGroupUserExited(Timestamp userGroupUserExited) {
        this.userGroupUserExited = userGroupUserExited;
    }
}
