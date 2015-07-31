package it.sevenbits.cards.web.domain;

public class SettingsForm {
    private String storeName;
    private int discountPercent;
    private String description;

    @Override
    public String toString() {
        return String.format("SettingsForm[storeName=%s, description=%s, discountPercent=%d]", storeName, description, discountPercent);
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName(){
        return this.storeName;
    }

    public void setDiscountPercent(int discountPercent){
        this.discountPercent = discountPercent;
    }

    public int getDiscountPercent(){
        return this.discountPercent;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
