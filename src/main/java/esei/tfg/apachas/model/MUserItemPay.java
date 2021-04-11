package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.UserItemPay;

public class MUserItemPay {

    private long userId;
    private long itemId;
    private Double payCost;

    public MUserItemPay() {
    }

    public MUserItemPay(UserItemPay userItemPay) {
        this.userId = userItemPay.getUserItemId().getUserId();
        this.itemId = userItemPay.getUserItemId().getItemId();
        this.payCost = userItemPay.getPayCost();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public Double getPayCost() {
        return payCost;
    }

    public void setPayCost(Double payCost) {
        this.payCost = payCost;
    }

}
