package esei.tfg.apachas.entity;

import esei.tfg.apachas.entity.id.UserItemId;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.io.Serializable;

@Table(name = "UserItemPay")
@Entity(name = "UserItemPay")
public class UserItemPay implements Serializable {

    @EmbeddedId
    private UserItemId userItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId")
    @JoinColumn(name = "itemId", referencedColumnName = "itemId", nullable = false)
    private Item item;

    @Column(name = "payCost", length = 11, nullable = false)
    @ColumnDefault(value = "0")
    private Double payCost;

    public UserItemPay() {
    }

    public UserItemPay(UserItemId userItemId, Double payCost) {
        this.userItemId = userItemId;
        this.payCost = payCost;
    }

    public UserItemId getUserItemId() {
        return userItemId;
    }

    public void setUserItemId(UserItemId userItemId) {
        this.userItemId = userItemId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getPayCost() {
        return payCost;
    }

    public void setPayCost(Double payCost) {
        this.payCost = payCost;
    }

}
