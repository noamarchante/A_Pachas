package esei.tfg.apachas.entity;

import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "event")
@Table(name = "event")
public class Event implements Serializable {

    //CLAVE PRIMARIA: ID_EVENTO
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "eventId")
    private long eventId;

    //ATRIBUTO: NOMBRE_EVENTO
    @Column(name = "eventName", nullable = false, length = 50)
    @Size(min = 4, max = 50)
    @NotNull
    @NotBlank
    private String eventName;

    //ATRIBUTO: DESCRIPCION_EVENTO
    @Column(name = "eventDescription", length = 155)
    @Size(min=0,max = 155)
    @NotBlank
    private String eventDescription;

    //ATRIBUTO: FECHA_INICIO_EVENTO
    @Column(name = "eventStartDate", nullable = false, length = 19)
    @Size(min = 16, max = 16)
    @NotNull
    @NotBlank
    private Timestamp eventStartDate;

    //ATRIBUTO: LUGAR
    @Column(name = "eventLocation", length = 155, nullable = false)
    @Size(min = 4, max = 155)
    @NotNull
    @NotBlank
    private String eventLocation;

    //ATRIBUTO: FOTO_EVENTO
    @Column(name = "eventPhoto")
    private String eventPhoto;

    //ATRIBUTO: ESTADO
    @Column(name = "eventState", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    @NotNull
    @NotBlank
    private boolean eventState;

    //ATRIBUTO: FECHA_FIN_EVENTO NOT NULL
    @Column(name = "eventEndDate", nullable = false, length = 19)
    @Size(min = 16, max = 16)
    @NotNull
    @NotBlank
    private Timestamp eventEndDate;

    //ATRIBUTO: IMPORTE_EVENTO
    @Column(name = "eventFinalPrice", nullable = false, length = 12)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 12)
    @ColumnDefault(value = "0")
    private double eventFinalPrice;

    //N:1 EVENTO ES CREADO POR USUARIO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    @NotNull
    @NotBlank
    private User user;

    //1:N EVENTO CONTIENE ELEMENTO
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @Transient
    private Set<Item> itemSet = new HashSet<>();

    //N:M EVENTO PARTICIPA USUARIO
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @Transient
    private Set<UserEventParticipate> userEventParticipateSet = new HashSet<>();

    public Event() {
    }

    public Event(long eventId, String eventName, String eventDescription, Timestamp eventStartDate, Timestamp eventEndDate, String eventLocation, String eventPhoto, boolean eventState, Double eventFinalPrice, User user) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventLocation = eventLocation;
        this.eventPhoto = eventPhoto;
        this.eventState = eventState;
        this.eventFinalPrice = eventFinalPrice;
        this.user = user;
    }

    public Event (long eventId){
        this.eventId = eventId;
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

    public Timestamp getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Timestamp eventStartDate) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Timestamp eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public double getEventFinalPrice() {
        return eventFinalPrice;
    }

    public void setEventFinalPrice(double eventFinalPrice) {
        this.eventFinalPrice = eventFinalPrice;
    }

    public Set<Item> getProductSet() {
        return itemSet;
    }

    public void setProductSet(Set<Item> itemSet) {
        this.itemSet = itemSet;
    }

    public Set<Item> getItemSet() {
        return itemSet;
    }

    public void setItemSet(Set<Item> itemSet) {
        this.itemSet = itemSet;
    }

    public Set<UserEventParticipate> getUserEventSet() {
        return userEventParticipateSet;
    }

    public void setUserEventSet(Set<UserEventParticipate> userEventParticipateSet) {
        this.userEventParticipateSet = userEventParticipateSet;
    }
}
