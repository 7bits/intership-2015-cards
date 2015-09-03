package it.sevenbits.cards.web.domain.forms;

public class SendForm {
    private String email;
    private String uin;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    @Override
    public String toString() {
        return String.format("BindForm[email=%s, uin=%s]", email, uin);
    }
}
