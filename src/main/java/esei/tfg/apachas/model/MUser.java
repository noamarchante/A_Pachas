package esei.tfg.apachas.model;

import esei.tfg.apachas.entity.User;
import java.sql.Timestamp;

public class MUser {

    private long userId;
    private String userName;
    private String userSurname;
    private String userLogin;
    private String userPassword;
    private String userEmail;
    private Timestamp userBirthday;
    private String userPhoto;
    private String roles;
    private String permissions;
    private Timestamp userCreation;
    private Timestamp userRemoval;
    private boolean userActive;

    public MUser() {
    }

    public MUser(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userSurname = user.getUserSurname();
        this.userLogin = user.getUserLogin();
        this.userPassword = user.getUserPassword();
        this.userEmail = user.getUserEmail();
        this.userBirthday = user.getUserBirthday();
        this.userPhoto = user.getUserPhoto();
        this.roles = user.getRoles();
        this.permissions = user.getPermissions();
        this.userCreation = user.getUserCreation();
        this.userRemoval = user.getUserRemoval();
        this.userActive = user.isUserActive();
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getUserBirthday() {
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

    public void setUserBirthday(Timestamp userBirthday) {
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
}
