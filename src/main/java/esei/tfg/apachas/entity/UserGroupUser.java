package esei.tfg.apachas.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import esei.tfg.apachas.entity.id.UserGroupUserId;
import javax.persistence.*;
import java.io.Serializable;
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
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date userGroupUserAdded;

    @Column(name = "userGroupUserExited")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date userGroupUserExited;

    public UserGroupUser() {

    }

    public UserGroupUser(UserGroupUserId userGroupUserId, Date userGroupUserExited) {
        this.userGroupUserId = userGroupUserId;
        this.userGroupUserAdded = new Date();
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

    public Date getUserGroupUserAdded() {
        return userGroupUserAdded;
    }

    public void setUserGroupUserAdded(Date userGroupUserAdded) {
        this.userGroupUserAdded = userGroupUserAdded;
    }

    public Date getUserGroupUserExited() {
        return userGroupUserExited;
    }

    public void setUserGroupUserExited(Date userGroupUserExited) {
        this.userGroupUserExited = userGroupUserExited;
    }
}
