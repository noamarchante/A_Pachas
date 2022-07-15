package esei.tfg.apachas.entity;

import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "product")
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "productId")
    private long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId", nullable = false)
    @NotNull
    @NotBlank
    private Event event;

    @Column(name = "productName", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    @NotNull
    @NotBlank
    private String productName;

    @Column(name = "productDescription", length = 155)
    @Size(min=0,max = 155)
    @NotBlank
    private String productDescription;

    @Column(name = "productCost", length = 12, nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 12)
    @ColumnDefault(value = "0")
    private Double productCost;

    @Column(name = "productQuantity", length = 12, nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 12)
    @ColumnDefault(value = "1")
    private int productQuantity;

    @Column(name = "productPhoto",  length = 100000)
    private String productPhoto;

    @Column(name = "productPurchaseDate", nullable = false, length = 19)
    @Size(min = 16, max = 16)
    @NotNull
    @NotBlank
    private Timestamp productPurchaseDate;

    @Column(name = "productCreation", length = 19, nullable = false)
    private Timestamp productCreation;

    @Column(name = "productRemoval", length = 19)
    private Timestamp productRemoval;

    @Column(name = "productActive", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean productActive;

    public Product() {
    }

    public Product(long productId, Event event, String productName, String productDescription, Double productCost, int productQuantity, String productPhoto, Timestamp productPurchaseDate) {
        this.productId = productId;
        this.setEvent(event);
        this.setProductName(productName);
        this.setProductDescription(productDescription);
        this.setProductCost(productCost);
        this.setProductQuantity(productQuantity);
        this.setProductPhoto(productPhoto);
        this.setProductPurchaseDate(productPurchaseDate);
        this.setProductActive(true);
        this.setProductCreation(new Timestamp(System.currentTimeMillis()));
        this.setProductRemoval(null);
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getProductCost() {
        return productCost;
    }

    public void setProductCost(Double productCost) {
        this.productCost = productCost;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    public Timestamp getProductPurchaseDate() {
        return productPurchaseDate;
    }

    public void setProductPurchaseDate(Timestamp productPurchaseDate) {
        this.productPurchaseDate = productPurchaseDate;
    }

    public Timestamp getProductCreation() {
        return productCreation;
    }

    public void setProductCreation(Timestamp productCreation) {
        this.productCreation = productCreation;
    }

    public Timestamp getProductRemoval() {
        return productRemoval;
    }

    public void setProductRemoval(Timestamp productRemoval) {
        this.productRemoval = productRemoval;
    }

    public boolean isProductActive() {
        return productActive;
    }

    public void setProductActive(boolean productActive) {
        this.productActive = productActive;
    }
}
