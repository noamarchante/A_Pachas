package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.Product;
import java.sql.Timestamp;

public class MProduct {

    private long productId;
    private String productName;
    private String productDescription;
    private Double productCost;
    private int productQuantity;
    private String productPhoto;
    private long eventId;
    private Timestamp productPurchaseDate;
    private boolean productActive;
    private Timestamp productCreation;
    private Timestamp productRemoval;

    public MProduct() {
    }

    public MProduct(Product product) {
        this.productId = product.getProductId();
        this.eventId = product.getEvent().getEventId();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.productCost = product.getProductCost();
        this.productQuantity = product.getProductQuantity();
        this.productPhoto = product.getProductPhoto();
        this.productPurchaseDate = product.getProductPurchaseDate();
        this.productActive = product.isProductActive();
        this.productCreation = product.getProductCreation();
        this.productRemoval = product.getProductRemoval();
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public Timestamp getProductPurchaseDate() {
        return productPurchaseDate;
    }

    public void setProductPurchaseDate(Timestamp productPurchaseDate) {
        this.productPurchaseDate = productPurchaseDate;
    }

    public boolean isProductActive() {
        return productActive;
    }

    public void setProductActive(boolean productActive) {
        this.productActive = productActive;
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
}
