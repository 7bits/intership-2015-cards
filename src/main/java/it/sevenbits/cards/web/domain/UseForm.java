package it.sevenbits.cards.web.domain;

public class UseForm {
    private String uin;

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    @Override
    public String toString() {
        return String.format("BindForm[uin=%s]", uin);
    }
}
