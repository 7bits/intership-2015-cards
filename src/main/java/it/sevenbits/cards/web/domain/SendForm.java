package it.sevenbits.cards.web.domain;
public class SendForm {
    private String userName;
    private String uin;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    @Override
    public String toString() {
        return String.format("BindForm[userName=%s, uin=%s]", userName, uin);
    }
}
