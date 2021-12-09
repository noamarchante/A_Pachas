package esei.tfg.apachas.entity;

import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
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
    @Column(name = "eventStart", nullable = false, length = 19)
    private Timestamp eventStart;

    //ATRIBUTO: LUGAR-EVENTO
    @Column(name = "eventLocation", length = 155, nullable = false)
    @Size(min = 4, max = 155)
    @NotNull
    @NotBlank
    private String eventLocation;

    //ATRIBUTO: FOTO_EVENTO
    @Column(name = "eventPhoto", length = 100000)
    private String eventPhoto;

    //ATRIBUTO: EVENTO ABIERTO (POR ESTAR DENTRO DE FECHA O PAGOS PENDIENTES)
    @Column(name = "eventOpen", length = 1)
    @Size(min = 1, max = 1)

    private boolean eventOpen;

    //ATRIBUTO: FECHA_FIN_EVENTO
    @Column(name = "eventEnd", nullable = false, length = 19)
    private Timestamp eventEnd;


    //ATRIBUTO: EVENTO ACTIVO (SI SE HA ELIMINADO O NO)
    @Column(name = "eventActive", length = 1)
    @Size(min = 1, max = 1)
    private boolean eventActive;

    //ATRIBUTO: FECHA_CREACIÓN_EVENTO
    @Column(name = "eventCreation", length = 19)
    private Timestamp eventCreation;

    //ATRIBUTO: FECHA_BORRADO_EVENTO
    @Column(name = "eventRemoval", length = 19)
    private Timestamp eventRemoval;


    //N:1 EVENTO ES CREADO POR USUARIO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventOwner", referencedColumnName = "userId", nullable = false)
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
    private Set<UserEvent> userEventSet = new HashSet<>();

    public Event() {
    }

    public Event(long eventId, String eventName, String eventDescription, Timestamp eventStart, Timestamp eventEnd, String eventLocation, String eventPhoto, User user) {
        this.eventId = eventId;
        this.setEventName(eventName);
        this.setEventDescription(eventDescription);
        this.setEventStart(eventStart);
        this.setEventEnd(eventEnd);
        this.setEventLocation(eventLocation);
        this.setEventPhoto(eventPhoto);
        this.setUser(user);
        this.setEventOpen(true);
        this.setEventActive(true);
        this.setEventCreation(new Timestamp(System.currentTimeMillis()));
        this.setEventRemoval(null);


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(Timestamp eventEnd) {
        this.eventEnd = eventEnd;
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

    public Set<UserEvent> getUserEventSet() {
        return userEventSet;
    }

    public void setUserEventSet(Set<UserEvent> userEventSet) {
        this.userEventSet = userEventSet;
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

    public void setEventOpen(boolean eventOpen) {
        this.eventOpen = eventOpen;
    }
}
