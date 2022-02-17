package esei.tfg.apachas.entity.id;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserProductId implements Serializable {

    private long productId;

    private long userId;

    public UserProductId() {
    }

    public UserProductId(long productId, long userId) {
        this.userId = userId;
        this.productId = productId;
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
}
