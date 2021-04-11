package esei.tfg.apachas.entity.id;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserGroupUserId implements Serializable {
    private long userGroupId;

    private long userId;

    public UserGroupUserId() {
    }

    public UserGroupUserId(long userGroupId, long userId) {
        this.userId = userId;
        this.userGroupId = userGroupId;
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
