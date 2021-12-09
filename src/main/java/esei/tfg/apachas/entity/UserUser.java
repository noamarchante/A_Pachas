package esei.tfg.apachas.entity;

import esei.tfg.apachas.entity.id.UserUserId;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

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
    @MapsId("friendId")
    @JoinColumn(name = "friendId", referencedColumnName = "userId", nullable = false)
    private User friend;

    @Column(name = "accept")
    private boolean accept;

    @Column(name = "userUserCreation")
    private Timestamp userUserCreation;

    @Column(name = "userUserRemoval")
    private Timestamp userUserRemoval;

    //ATRIBUTO: USUARIO DE EVENTO ACTIVO (SI SE HA ELIMINADO O NO)
    @Column(name = "userUserActive", length = 1)
    @Size(min = 1, max = 1)
    private boolean userUserActive;

    public UserUser (){

    }
    public UserUser(UserUserId userUserId, boolean accept) {
        this.userUserId = userUserId;
        this.accept = accept;
        this.setUserUserActive(true);
        this.setUserUserCreation(new Timestamp(System.currentTimeMillis()));
        this.setUserUserRemoval(null);
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

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public Timestamp getUserUserCreation() {
        return userUserCreation;
    }

    public void setUserUserCreation(Timestamp userUserCreation) {
        this.userUserCreation = userUserCreation;
    }

    public Timestamp getUserUserRemoval() {
        return userUserRemoval;
    }

    public void setUserUserRemoval(Timestamp userUserRemoval) {
        this.userUserRemoval = userUserRemoval;
    }

    public boolean isUserUserActive() {
        return userUserActive;
    }

    public void setUserUserActive(boolean userUserActive) {
        this.userUserActive = userUserActive;
    }
}
