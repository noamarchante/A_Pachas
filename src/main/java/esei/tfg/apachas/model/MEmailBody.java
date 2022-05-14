package esei.tfg.apachas.model;

public class MEmailBody {
    private String content;
    private String email;
    private String subject;

    public MEmailBody(String content, String email, String subject){
        this.content = content;
        this.email = email;
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
