package esei.tfg.apachas.model;

import esei.tfg.apachas.entity.User;

import java.util.Date;


public class MUser {

    private long userId;
    private String userName;
    private String userSurname;
    private String userLogin;
    private String userPassword;
    private String userEmail;
    private Date userBirthday;
    private String userPhoto;

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