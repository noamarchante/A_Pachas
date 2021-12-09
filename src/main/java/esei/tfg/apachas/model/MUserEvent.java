package esei.tfg.apachas.model;


import esei.tfg.apachas.entity.UserEventParticipate;

public class MUserEventParticipate {
    private long eventId;
    private long userId;
    private boolean accept;
    private Double totalDebt;

    public MUserEventParticipate() {
    }

    public MUserEventParticipate(UserEventParticipate userEventParticipate) {
        this.eventId = userEventParticipate.getUserEventId().getEventId();
        this.userId = userEventParticipate.getUserEventId().getUserId();
        this.accept = userEventParticipate.getAccept();
        this.totalDebt = userEventParticipate.getTotalDebt();
    }


    public boolean getAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public Double getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(Double totalDebt) {
        this.totalDebt = totalDebt;
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
}
