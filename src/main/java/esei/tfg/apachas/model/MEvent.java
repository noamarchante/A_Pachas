package esei.tfg.apachas.model;

import esei.tfg.apachas.entity.Event;
import java.sql.Timestamp;

public class MEvent {

    private long eventId;
    private String eventName;
    private String eventDescription;
    private Timestamp eventStart;
    private Timestamp eventEnd;
    private String eventLocation;
    private String eventPhoto;
    private boolean eventOpen;
    private long eventOwner;
    private boolean eventActive;
    private Timestamp eventCreation;
    private Timestamp eventRemoval;

    public MEvent() {
    }

    public MEvent(Event event) {
        this.eventId = event.getEventId();
        this.eventName = event.getEventName();
        this.eventDescription = event.getEventDescription();
        this.eventStart = event.getEventStart();
        this.eventEnd = event.getEventEnd();
        this.eventLocation = event.getEventLocation();
        this.eventPhoto = event.getEventPhoto();
        this.eventOpen = event.isEventOpen();
        this.eventOwner = event.getUser().getUserId();
        this.eventActive = event.isEventActive();
        this.eventCreation = event.getEventCreation();
        this.eventRemoval = event.getEventRemoval();
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

    public Timestamp getEventStart() {
        return eventStart;
    }

    public void setEventStart(Timestamp eventStart) {
        this.eventStart = eventStart;
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

    public boolean getEventOpen() {
        return eventOpen;
    }


    public void setEventOpen(boolean eventOpen) {
        this.eventOpen = eventOpen;
    }

    public long getEventOwner() {
        return eventOwner;
    }

    public void setEventOwner(long eventOwner) {
        this.eventOwner = eventOwner;
    }

    public Timestamp getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(Timestamp eventEnd) {
        this.eventEnd = eventEnd;
    }

    public boolean isEventOpen() {
        return eventOpen;
    }

    public boolean isEventActive() {
        return eventActive;
    }

    public void setEventActive(boolean eventActive) {
        this.eventActive = eventActive;
    }

    public Timestamp getEventCreation() {
        return eventCreation;
    }

    public void setEventCreation(Timestamp eventCreation) {
        this.eventCreation = eventCreation;
    }

    public Timestamp getEventRemoval() {
        return eventRemoval;
    }

    public void setEventRemoval(Timestamp eventRemoval) {
        this.eventRemoval = eventRemoval;
    }
}
