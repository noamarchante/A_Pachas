package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.Item;

import java.sql.Timestamp;
import java.util.Date;

public class MItem {

    private long itemId;
    private String itemName;
    private String itemDescription;
    private Double itemCost;
    private int itemQuantity;
    private String itemUrl;
    private String itemPhoto;
    private long eventId;
    private Timestamp itemPurchaseDate;

    public MItem() {
    }

    public MItem(Item item) {
        this.itemId = item.getItemId();
        this.eventId = item.getEvent().getEventId();
        this.itemName = item.getItemName();
        this.itemDescription = item.getItemDescription();
        this.itemCost = item.getItemCost();
        this.itemQuantity = item.getItemQuantity();
        this.itemUrl = item.getItemUrl();
        this.itemPhoto = item.getItemPhoto();
        this.itemPurchaseDate = item.getItemPurchaseDate();
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public void setItemCost(Double itemCost) {
        this.itemCost = itemCost;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getItemPhoto() {
        return itemPhoto;
    }

    public void setItemPhoto(String itemPhoto) {
        this.itemPhoto = itemPhoto;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public Timestamp getItemPurchaseDate() {
        return itemPurchaseDate;
    }

    public void setItemPurchaseDate(Timestamp itemPurchaseDate) {
        this.itemPurchaseDate = itemPurchaseDate;
    }
}
