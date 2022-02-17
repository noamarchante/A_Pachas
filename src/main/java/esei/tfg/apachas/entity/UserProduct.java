package esei.tfg.apachas.entity;

import esei.tfg.apachas.entity.id.UserProductId;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "userProduct")
@Table(name = "userProduct")
public class UserProduct implements Serializable {
    @EmbeddedId
    private UserProductId userProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "productId", referencedColumnName = "productId", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @Column(name = "userProductCreation")
    private Timestamp userProductCreation;

    @Column(name = "userProductRemoval")
    private Timestamp userProductRemoval;

    @Column(name = "userProductActive", length = 1)
    @Size(min = 1, max = 1)
    private boolean userProductActive;

    public UserProduct() {
    }

    public UserProduct(UserProductId userProductId) {
        this.userProductId = userProductId;
        this.setUserProductActive(false);
        this.setUserProductRemoval(null);
        this.setUserProductCreation(new Timestamp(System.currentTimeMillis()));
    }

    public UserProductId getUserProductId() {
        return userProductId;
    }

    public void setUserProductId(UserProductId userProductId) {
        this.userProductId = userProductId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getUserProductCreation() {
        return userProductCreation;
    }

    public void setUserProductCreation(Timestamp userProductCreation) {
        this.userProductCreation = userProductCreation;
    }

    public Timestamp getUserProductRemoval() {
        return userProductRemoval;
    }

    public void setUserProductRemoval(Timestamp userProductRemoval) {
        this.userProductRemoval = userProductRemoval;
    }

    public boolean isUserProductActive() {
        return userProductActive;
    }

    public void setUserProductActive(boolean userProductActive) {
        this.userProductActive = userProductActive;
    }
}
