package esei.tfg.apachas.entity;

import esei.tfg.apachas.entity.id.UserItemId;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "UserItemDebt")
@Entity(name = "UserItemDebt")
public class UserItemDebt implements Serializable {

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

    @Column( name = "debtCost", nullable = false)
    private Double debtCost;

    public UserItemDebt() {
    }

    public UserItemDebt(UserItemId userItemId, Double debtCost) {
        this.userItemId = userItemId;
        this.debtCost = debtCost;
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

    public Double getDebtCost() {
        return debtCost;
    }

    public void setDebtCost(Double debtCost) {
        this.debtCost = debtCost;
    }
}
