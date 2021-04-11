package esei.tfg.apachas.entity;

import esei.tfg.apachas.entity.id.UserUserId;
import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "userUser")
@Table(name = "userUser")
public class UserUser implements Serializable {
    @EmbeddedId
    private UserUserId userUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User friend;

    public UserUser (){

    }
    public UserUser(UserUserId userUserId) {
        this.userUserId = userUserId;
    }

    public UserUserId getUserUserId() {
        return userUserId;
    }

    public void setUserUserId(UserUserId userUserId) {
        this.userUserId = userUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}