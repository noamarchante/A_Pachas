package esei.tfg.apachas.entity;

import esei.tfg.apachas.entity.id.UserEventId;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity(name = "userEventParticipate")
@Table(name = "userEventParticipate")
public class UserEventParticipate implements Serializable {
    @EmbeddedId
    private UserEventId userEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "eventId", referencedColumnName = "eventId", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @Column(name = "accept", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    @NotNull
    @NotBlank
    private boolean accept;

    @Column(name = "totalDebt", length = 12, nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 12)
    @ColumnDefault(value = "0")
    private Double totalDebt;

    public UserEventParticipate() {
    }

    public UserEventParticipate(UserEventId userEventId, boolean accept, Double totalDebt) {
        this.userEventId = userEventId;
        this.accept = accept;
        this.totalDebt = totalDebt;
    }

    public UserEventId getUserEventId() {
        return userEventId;
    }

    public void setUserEventId(UserEventId userEventId) {
        this.userEventId = userEventId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
