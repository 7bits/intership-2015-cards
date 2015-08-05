package it.sevenbits.cards.web.domain;

public class GenerateDiscountForm {
    private String storeName;
    private String discountPercent;
    private String userName;
    private String description;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("GenerateDiscountForm[storeName=%s, discountPercent=%s, userName=%s, description=%s]", storeName, discountPercent, userName, description);
    }
}