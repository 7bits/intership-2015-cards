package it.sevenbits.cards.web.domain;

public class SettingsForm {
    private String storeName;
    private String discountPercent;
    private String description;

    @Override
    public String toString() {
        return String.format("SettingsForm[storeName=%s, description=%s, discountPercent=%s]", storeName, description, discountPercent);
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName(){
        return this.storeName;
    }

    public void setDiscountPercent(String discountPercent){
        this.discountPercent = discountPercent;
    }

    public String getDiscountPercent(){
        return this.discountPercent;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
