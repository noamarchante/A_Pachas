package esei.tfg.apachas.entity;

import esei.tfg.apachas.entity.id.UserGroupUserId;
import javax.persistence.*;
import java.io.Serializable;

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

    public UserGroupUser() {
    }

    public UserGroupUser(UserGroupUserId userGroupUserId) {
        this.userGroupUserId = userGroupUserId;
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
}
