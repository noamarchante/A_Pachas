package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.UserProduct;
import java.sql.Timestamp;

public class MUserProduct {
    private long productId;
    private long userId;
    private Timestamp userProductCreation;
    private Timestamp userProductRemoval;
    private boolean userProductActive;

    public MUserProduct() {
    }

    public MUserProduct(UserProduct userProduct) {
        this.productId = userProduct.getUserProductId().getProductId();
        this.userId = userProduct.getUserProductId().getUserId();
        this.userProductActive = userProduct.isUserProductActive();
        this.userProductCreation = userProduct.getUserProductCreation();
        this.userProductRemoval = userProduct.getUserProductRemoval();
    }


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
