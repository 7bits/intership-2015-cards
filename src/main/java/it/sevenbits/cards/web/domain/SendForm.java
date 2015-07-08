package it.sevenbits.cards.web.domain;
public class SendForm {
    private String userName;
    private int uin;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUin() {
        return uin;
    }

    public void setUin(int uin) {
        this.uin = uin;
    }

    @Override
    public String toString() {
        return String.format("BindForm[userName=%s, uin=%s]", userName, uin);
    }
}
