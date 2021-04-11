package esei.tfg.apachas.entity.id;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserEventId implements Serializable {

    private long eventId;

    private long userId;

    public UserEventId() {
    }

    public UserEventId(long eventId, long userId) {
        this.userId = userId;
        this.eventId = eventId;
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
