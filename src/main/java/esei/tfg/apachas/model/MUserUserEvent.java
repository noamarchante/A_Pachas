package esei.tfg.apachas.model;

import esei.tfg.apachas.entity.UserUserEvent;
import java.sql.Timestamp;

public class MUserUserEvent {

    private long senderId;
    private long receiverId;
    private long eventId;
    private boolean paid;
    private boolean confirmed;
    private double cost;
    private Timestamp userUserEventCreation;
    private Timestamp userUserEventRemoval;
    private boolean userUserEventActive;

    public MUserUserEvent (){}

    public MUserUserEvent (UserUserEvent userUserEvent){
        this.senderId = userUserEvent.getUserUserEventId().getSenderId();
        this.receiverId = userUserEvent.getUserUserEventId().getReceiverId();
        this.eventId = userUserEvent.getUserUserEventId().getEventId();
        this.paid = userUserEvent.isPaid();
        this.confirmed = userUserEvent.isConfirmed();
        this.cost = userUserEvent.getCost();
        this.userUserEventActive = userUserEvent.isUserUserEventActive();
        this.userUserEventCreation = userUserEvent.getUserUserEventCreation();
        this.userUserEventRemoval = userUserEvent.getUserUserEventRemoval();
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Timestamp getUserUserEventCreation() {
        return userUserEventCreation;
    }

    public void setUserUserEventCreation(Timestamp userUserEventCreation) {
        this.userUserEventCreation = userUserEventCreation;
    }

    public Timestamp getUserUserEventRemoval() {
        return userUserEventRemoval;
    }

    public void setUserUserEventRemoval(Timestamp userUserEventRemoval) {
        this.userUserEventRemoval = userUserEventRemoval;
    }

    public boolean isUserUserEventActive() {
        return userUserEventActive;
    }

    public void setUserUserEventActive(boolean userUserEventActive) {
        this.userUserEventActive = userUserEventActive;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
}
