package esei.tfg.apachas.entity;

import esei.tfg.apachas.entity.id.UserEventId;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "userEvent")
@Table(name = "userEvent")
public class UserEvent implements Serializable {
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

    @Column(name = "accept", length = 1)
    @Size(min = 1, max = 1)
    private boolean accept;

    @Column(name = "totalExpense", length = 12, nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 12)
    @ColumnDefault(value = "0")
    private Double totalExpense;

    @Column(name = "userEventCreation")
    private Timestamp userEventCreation;

    @Column(name = "userEventRemoval")
    private Timestamp userEventRemoval;

    //ATRIBUTO: USUARIO DE EVENTO ACTIVO (SI SE HA ELIMINADO O NO)
    @Column(name = "userEventActive", length = 1)
    @Size(min = 1, max = 1)
    private boolean userEventActive;

    public UserEvent() {
    }

    public UserEvent(UserEventId userEventId, Double totalExpense, boolean accept) {
        this.userEventId = userEventId;
        this.setTotalExpense(totalExpense);
        this.setAccept(accept);
        this.setUserEventActive(false);
        this.setUserEventRemoval(null);
        this.setUserEventCreation(new Timestamp(System.currentTimeMillis()));
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

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalDebt) {
        this.totalExpense = totalDebt;
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
