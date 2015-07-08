package it.sevenbits.cards.web.domain;

public class UseForm {
    private int uin;

    public int getUin() {
        return uin;
    }

    public void setUin(int uin) {
        this.uin = uin;
    }

    @Override
    public String toString() {
        return String.format("BindForm[uin=%s]", uin);
    }
}
