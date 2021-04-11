package esei.tfg.apachas.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "item")
@Table(name = "item")
public class Item implements Serializable {

    //CLAVE PRIMARIA: ID_ELEMENTO
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "itemId")
    private long itemId;

    //N:1 ELEMENTO ES CONTENIDO EN EVENTO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId", nullable = false)
    @NotNull
    @NotBlank
    private Event event;

    //ATRIBUTO: NOMBRE_ELEMENTO
    @Column(name = "itemName", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[[:alpha:]áéíóúÁÉÍÓÚñÑ]+(?:[[:space:]][[:alnum:]áéíóúÁÉÍÓÚñÑ]+)*$")
    private String itemName;

    //ATRIBUTO: DESCRIPCION_ELEMENTO
    @Column(name = "itemDescription", length = 155)
    @Size(min=0,max = 155)
    @NotBlank
    @Pattern(regexp = "^[[:alpha:]áéíóúÁÉÍÓÚñÑ]+(?:[[:space:]][[:alnum:]áéíóúÁÉÍÓÚñÑ]+)*$")
    private String itemDescription;

    //ATRIBUTO: COSTE
    @Column(name = "itemCost", length = 12, nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 12)
    @Pattern(regexp = "^[0-9]+([.][0-9]+)?$")
    @ColumnDefault(value = "0")
    private Double itemCost;

    //ATRIBUTO: CANTIDAD
    @Column(name = "itemQuantity", length = 12, nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 12)
    @Pattern(regexp = "^[0-9]+$")
    @ColumnDefault(value = "1")
    private int itemQuantity;

    //ATRIBUTO: URL
    @Column(name = "itemUrl", length = 2083)
    @NotBlank
    @Size(min = 0, max = 2083)
    @Pattern(regexp = "^((https|http)?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$")
    private String itemUrl;

    //ATRIBUTO: FOTO
    @Column(name = "itemPhoto")
    private String itemPhoto;

    //ATRIBUTO: FECHA_ADQUISICIÓN
    @Column(name = "itemPurchaseDate", nullable = false, length = 19)
    @Size(min = 16, max = 16)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    @NotBlank
    @Pattern(regexp = "^([0-2][0-9]|3[0-1])(-)(0[1-9]|1[0-2])\\2(\\d{4})$")
    private Date itemPurchaseDate;

    //N:M ELEMENTO ES PAGADO POR USUARIO
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @Transient
    private Set<UserItemPay> userItemPaySet = new HashSet<>();

    //N:M ELEMENTO ES DEBIDO POR USUARIO
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @Transient
    private Set<UserItemDebt> userItemDebtSet = new HashSet<>();

    public Item() {
    }

    public Item(long itemId,Event event, String itemName, String itemDescription, Double itemCost, int itemQuantity, String itemUrl, String itemPhoto, Date itemPurchaseDate) {
        this.itemId = itemId;
        this.event = event;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCost = itemCost;
        this.itemQuantity = itemQuantity;
        this.itemUrl = itemUrl;
        this.itemPhoto = itemPhoto;
        this.itemPurchaseDate = itemPurchaseDate;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public void setItemCost(Double itemCost) {
        this.itemCost = itemCost;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getItemPhoto() {
        return itemPhoto;
    }

    public void setItemPhoto(String itemPhoto) {
        this.itemPhoto = itemPhoto;
    }

    public Date getItemPurchaseDate() {
        return itemPurchaseDate;
    }

    public void setItemPurchaseDate(Date itemPurchaseDate) {
        this.itemPurchaseDate = itemPurchaseDate;
    }

    public Set<UserItemPay> getUserItemPaySet() {
        return userItemPaySet;
    }

    public void setUserItemPaySet(Set<UserItemPay> userItemPaySet) {
        this.userItemPaySet = userItemPaySet;
    }

    public Set<UserItemDebt> getUserItemDebtSet() {
        return userItemDebtSet;
    }

    public void setUserItemDebtSet(Set<UserItemDebt> userItemDebtSet) {
        this.userItemDebtSet = userItemDebtSet;
    }
}
