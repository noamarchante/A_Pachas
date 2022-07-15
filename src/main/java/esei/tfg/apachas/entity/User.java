package esei.tfg.apachas.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity(name = "user")
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "userId")
    private long userId;

    @Column(name = "userName", length = 50, nullable = false)
    @Size(min = 3, max = 50)
    @NotNull
    @NotBlank
    private String userName;

    @Column(name = "userSurname", length = 50, nullable = false)
    @Size(min = 4, max = 50)
    @NotNull
    @NotBlank
    private String userSurname;

    @Column(name = "userLogin", length = 14, nullable = false, unique = true)
    @Size(min = 4, max = 15)
    @NotBlank
    @NotNull
    private String userLogin;

    @Column(name = "userPassword", nullable = false)
    @NotNull
    @NotBlank
    private String userPassword;

    @Column(name = "userEmail", length = 320, nullable = false, unique = true)
    @NotNull
    @NotBlank
    @Email
    private String userEmail;

    @Column(name = "userBirthday")
    private Timestamp userBirthday;

    @Lob
    @Column(name = "userPhoto", length = 100000)
    private String userPhoto;

    @Column(name = "roles", nullable = false)
    private String roles = "";

    @Column(name = "permissions")
    private String permissions = "";

    @Column(name = "userCreation")
    private Timestamp userCreation;

    @Column(name = "userRemoval")
    private Timestamp userRemoval;

    @Column(name = "userActive", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean userActive;

    @Column(name = "userVerified", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean userVerified;

    @Column(name = "tokenPassword")
    private String tokenPassword;

    @Column(name = "userNotify", length = 1, nullable = false)
    @Size(min = 1, max = 1)
    private boolean userNotify;

    //N:M USUARIO RELACIONADO CON USUARIO
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Transient
    private Set<UserUser> userUserSet = new HashSet<>();

    //N:M USUARIO RELACIONADO CON USUARIO
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Transient
    private Set<UserUser> userFriendSet = new HashSet<>();

    //N:M USUARIO PARTICIPA EN EVENTO
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Transient
    private Set<UserEvent> userEventSet = new HashSet<>();

    //1:N USUARIO CREA EVENTO
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Transient
    private Set<Event> eventSet = new HashSet<>();

    //N:M USUARIO INTEGRADO EN GRUPO
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Transient
    private Set<GroupUser> groupUserSet = new HashSet<>();

    /*//N:M USUARIO emite pago evento
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    @Transient
    private Set<UserUserEvent> senderSet = new HashSet<>();

    //N:M USUARIO recive pago evento
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    @Transient
    private Set<UserUserEvent> receiverSet = new HashSet<>();*/

    public User() {
    }

    public User(long userId, String userName, String userSurname, String userLogin, String userPassword, String userEmail, Timestamp userBirthday, String userPhoto, String roles, String permissions) {
        this.userId = userId;
        this.setUserName(userName);
        this.setUserSurname(userSurname);
        this.setUserLogin(userLogin);
        this.setUserPassword(userPassword);
        this.setUserEmail(userEmail);
        this.setUserBirthday(userBirthday);
        this.setUserPhoto(userPhoto);
        this.setPermissions(permissions);
        this.setRoles(roles);
        this.setUserCreation(new Timestamp(System.currentTimeMillis()));
        this.setUserRemoval(null);
        this.setUserActive(true);
        this.setUserVerified(false);
        this.setUserNotify(true);
    }

    public boolean isUserNotify() {
        return userNotify;
    }

    public void setUserNotify(boolean userNotify) {
        this.userNotify = userNotify;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }

    public boolean isUserVerified() {
        return userVerified;
    }

    public void setUserVerified(boolean userVerified) {
        this.userVerified = userVerified;
    }

    public User(long userId){
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Timestamp getUserBirthday() {
        return userBirthday;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public Set<UserUser> getUserUserSet() {
        return userUserSet;
    }

    public Set<UserUser> getUserFriendSet() {
        return userFriendSet;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserBirthday(Timestamp userBirthday) { this.userBirthday = userBirthday; }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void setUserUserSet(Set<UserUser> userUserSet) {
        this.userUserSet = userUserSet;
    }

    public void setUserFriendSet(Set<UserUser> userFriendSet) {
        this.userFriendSet = userFriendSet;
    }

    public Set<UserEvent> getUserEventSet() {
        return userEventSet;
    }

    public void setUserEventSet(Set<UserEvent> userEventSet) {
        this.userEventSet = userEventSet;
    }

    public Set<Event> getEventSet() {
        return eventSet;
    }

    public void setEventSet(Set<Event> eventSet) {
        this.eventSet = eventSet;
    }


    public Set<GroupUser> getUserGroupUserSet() {
        return groupUserSet;
    }

    public void setUserGroupUserSet(Set<GroupUser> groupUserSet) {
        this.groupUserSet = groupUserSet;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getUserCreation() {
        return userCreation;
    }

    public void setUserCreation(Timestamp userCreation) {
        this.userCreation = userCreation;
    }

    public Timestamp getUserRemoval() {
        return userRemoval;
    }

    public void setUserRemoval(Timestamp userRemoval) {
        this.userRemoval = userRemoval;
    }

    public boolean isUserActive() {
        return userActive;
    }

    public void setUserActive(boolean userActive) {
        this.userActive = userActive;
    }

    public Set<GroupUser> getGroupUserSet() {
        return groupUserSet;
    }

    public void setGroupUserSet(Set<GroupUser> groupUserSet) {
        this.groupUserSet = groupUserSet;
    }
}
