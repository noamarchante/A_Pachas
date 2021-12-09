package esei.tfg.apachas.entity.id;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GroupUserId implements Serializable {
    private long groupId;

    private long userId;

    public GroupUserId() {
    }

    public GroupUserId(long groupId, long userId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
