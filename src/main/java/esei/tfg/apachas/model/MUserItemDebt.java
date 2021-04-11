package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.UserItemDebt;

public class MUserItemDebt {

    private long userId;
    private long itemId;
    private Double debtCost;

    public MUserItemDebt() {
    }

    public MUserItemDebt(UserItemDebt userItemDebt) {
        this.userId = userItemDebt.getUserItemId().getUserId();
        this.itemId = userItemDebt.getUserItemId().getItemId();
        this.debtCost = userItemDebt.getDebtCost();

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

    public Double getDebtCost() {
        return debtCost;
    }

    public void setDebtCost(Double debtCost) {
        this.debtCost = debtCost;
    }
}
