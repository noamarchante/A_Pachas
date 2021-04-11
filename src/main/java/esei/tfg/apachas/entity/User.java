package esei.tfg.apachas.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "user")
@Table(name = "user")
public class User implements Serializable {

    //CLAVE PRIMARIA: ID_USUARIO
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "userId")
    private long userId;

    //ATRIBUTO: NOMBRE_USUARIO
    @Column(name = "userName", length = 50, nullable = false)
    @Size(min = 3, max = 50)
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[[:alpha:]áéíóúÁÉÍÓÚñÑ]+(?:[[:space:]][[:alnum:]áéíóúÁÉÍÓÚñÑ]+)*$")
    private String userName;

    //ATRIBUTO: APELLIDOS
    @Column(name = "userSurname", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[[:alpha:]áéíóúÁÉÍÓÚñÑ]+(?:[[:space:]][[:alnum:]áéíóúÁÉÍÓÚñÑ]+)*$")
    private String userSurname;

    //ATRIBUTO: LOGIN
    @Column(name = "userLogin", length = 15, nullable = false, unique = true)
    @Size(min = 4, max = 15)
    @NotBlank
    @NotNull
    @Pattern(regexp = "^\\w+$")
    private String userLogin;

    //ATRIBUTO: CONTRASEÑA
    @Column(name = "userPassword", nullable = false)
    private String userPassword;

    //ATRIBUTO: EMAIL
    @Column(name = "userEmail", length = 320, nullable = false, unique = true)
    @NotNull
    @NotBlank
    @Email
    private String userEmail;

    //ATRIBUTO: FECHA_NACIMIENTO
    @Column(name = "userBirthday", length = 19)
    @Size(min = 16, max = 16)
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    @NotBlank
    @Pattern(regexp = "^([0-2][0-9]|3[0-1])(-)(0[1-9]|1[0-2])\\2(\\d{4})$")
    private Date userBirthday;

    //ATRIBUTO: FOTO_USUARIO
    @Column(name = "userPhoto")
    private String userPhoto;

    public User() {
    }

    public User(long userId, String userName, String userSurname, String userLogin, String userPassword, String userEmail, Date userBirthday, String userPhoto) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userBirthday = userBirthday;
        this.userPhoto = userPhoto;
    }

    public User( String userName, String userSurname, String userLogin, String userPassword, String userEmail, Date userBirthday, String userPhoto) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userBirthday = userBirthday;
        this.userPhoto = userPhoto;
    }

    public User (long userId){
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }
}
