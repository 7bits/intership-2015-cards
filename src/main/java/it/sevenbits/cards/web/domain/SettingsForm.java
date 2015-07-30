package it.sevenbits.cards.web.domain;

/**
 * Created by taro on 30.07.15.
 */
public class SettingsForm {
    private String storeName;
    private int discount;
    private String describe;

    public String getDescribe() {
        return describe;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public String toString() {
        return String.format("SettingsForm[storeName=%s, describe=%s, discount=%d]", storeName, describe, discount);
    }
}
