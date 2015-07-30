package it.sevenbits.cards.web.domain;

public class UseForm {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return String.format("UseForm[key=%s]", key);
    }
}
