package esei.tfg.apachas.model;

import esei.tfg.apachas.entity.Event;

import java.util.Date;

public class MEvent {

    private long eventId;
    private String eventName;
    private String eventDescription;
    private Date eventStartDate;
    private Date eventEndDate;
    private String eventLocation;
    private String eventPhoto;
    private boolean eventState;
    private long userId;
    private Double eventFinalPrice;

    public MEvent() {
    }

    public MEvent(Event event) {
        this.eventId = event.getEventId();
        this.eventName = event.getEventName();
        this.eventDescription = event.getEventDescription();
        this.eventStartDate = event.getEventStartDate();
        this.eventEndDate = event.getEventEndDate();
        this.eventLocation = event.getEventLocation();
        this.eventPhoto = event.getEventPhoto();
        this.eventState = event.getEventState();
        this.eventFinalPrice = event.getEventFinalPrice();
        this.userId = event.getUser().getUserId();
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Date getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventPhoto() {
        return eventPhoto;
    }

    public void setEventPhoto(String eventPhoto) {
        this.eventPhoto = eventPhoto;
    }

    public boolean getEventState() {
        return eventState;
    }


    public void setEventState(boolean eventState) {
        this.eventState = eventState;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Date eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public Double getEventFinalPrice() {
        return eventFinalPrice;
    }

    public void setEventFinalPrice(Double eventFinalPrice) {
        this.eventFinalPrice = eventFinalPrice;
    }
}
