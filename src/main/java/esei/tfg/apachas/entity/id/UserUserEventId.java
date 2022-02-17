package esei.tfg.apachas.entity.id;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserUserEventId implements Serializable {

    private long senderId;

    private long receiverId;

    private long eventId;

    public UserUserEventId (){

    }

    public UserUserEventId(long senderId, long receiverId, long eventId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.eventId = eventId;
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

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }
}
