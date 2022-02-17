package esei.tfg.apachas.entity.id;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserUserId implements Serializable {

    private long userId;

    private long friendId;

    public UserUserId (){

    }

    public UserUserId(long userId, long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public long getUserId() {
        return userId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    public long getFriendId() {
        return friendId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
