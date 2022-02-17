package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.UserEvent;
import java.sql.Timestamp;

public class MUserEvent {
    private long eventId;
    private long userId;
    private boolean accept;
    private Double totalExpense;
    private Double debt;
    private Timestamp userEventCreation;
    private Timestamp userEventRemoval;
    private boolean userEventActive;

    public MUserEvent() {
    }

    public MUserEvent(UserEvent userEvent) {
        this.eventId = userEvent.getUserEventId().getEventId();
        this.userId = userEvent.getUserEventId().getUserId();
        this.accept = userEvent.isAccept();
        this.totalExpense = userEvent.getTotalExpense();
        this.debt = userEvent.getDebt();
        this.userEventActive = userEvent.isUserEventActive();
        this.userEventCreation = userEvent.getUserEventCreation();
        this.userEventRemoval = userEvent.getUserEventRemoval();
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isAccept() {
        return accept;
    }

    public Timestamp getUserEventCreation() {
        return userEventCreation;
    }

    public void setUserEventCreation(Timestamp userEventCreation) {
        this.userEventCreation = userEventCreation;
    }

    public Timestamp getUserEventRemoval() {
        return userEventRemoval;
    }

    public void setUserEventRemoval(Timestamp userEventRemoval) {
        this.userEventRemoval = userEventRemoval;
    }

    public boolean isUserEventActive() {
        return userEventActive;
    }

    public void setUserEventActive(boolean userEventActive) {
        this.userEventActive = userEventActive;
    }
}
