package it.sevenbits.cards.web.domain;
public class SendForm {
    private String userId;
    private String uin;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userName) {
        this.userId = userName;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    @Override
    public String toString() {
        return String.format("BindForm[userName=%s, uin=%s]", userId, uin);
    }
}
