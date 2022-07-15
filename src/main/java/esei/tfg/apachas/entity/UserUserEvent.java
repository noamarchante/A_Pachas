package esei.tfg.apachas.entity;

import esei.tfg.apachas.entity.id.UserUserEventId;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "userUserEvent")
@Table(name = "userUserEvent")
public class UserUserEvent implements Serializable {

    @EmbeddedId
    private UserUserEventId userUserEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("senderId")
    @JoinColumn(name = "senderId", referencedColumnName = "userId", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("receiverId")
    @JoinColumn(name = "receiverId", referencedColumnName = "userId", nullable = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "eventId", referencedColumnName = "eventId", nullable = false)
    private Event event;

    @Column(name = "paid", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean paid;

    @Column(name = "confirmed", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean confirmed;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "userUserEventCreation", nullable = false)
    private Timestamp userUserEventCreation;

    @Column(name = "userUserEventRemoval")
    private Timestamp userUserEventRemoval;

    @Column(name = "userUserEventActive", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean userUserEventActive;

    public UserUserEvent(){

    }

    public UserUserEvent(UserUserEventId userUserEventId, double cost, boolean paid, boolean confirmed) {
        this.userUserEventId = userUserEventId;
        this.setCost(cost);
        this.setPaid(paid);
        this.setConfirmed(confirmed);
        this.setUserUserEventActive(true);
        this.setUserUserEventCreation(new Timestamp(System.currentTimeMillis()));
        this.setUserUserEventRemoval(null);
    }

    public UserUserEventId getUserUserEventId() {
        return userUserEventId;
    }

    public void setUserUserEventId(UserUserEventId userUserEventId) {
        this.userUserEventId = userUserEventId;
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

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}