package esei.tfg.apachas.model;

public class MVerifyEmail {

    private String userEmail;
    private String tokenPassword;

    public MVerifyEmail(){}

    public MVerifyEmail(String userEmail, String tokenPassword){
        this.userEmail = userEmail;
        this.tokenPassword = tokenPassword;
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
