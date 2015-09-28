package it.sevenbits.cards.web.domain.forms;

public class KeyForm {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return String.format("KeyForm[key=%s]", key);
    }
}
