package esei.tfg.apachas.model;

public class MRetrievePassword {

    private String userEmail;
    private String tokenPassword;
    private String newPassword;

    public MRetrievePassword(){}

    public MRetrievePassword(String userEmail, String tokenPassword, String newPassword){
        this.userEmail = userEmail;
        this.tokenPassword = tokenPassword;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }
}
