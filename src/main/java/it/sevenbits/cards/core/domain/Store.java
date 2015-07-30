package it.sevenbits.cards.core.domain;

import java.io.Serializable;

public class Store implements Serializable {
    private Long id;
    private String userId;
    private String storeName;
    private String storeImage;
    private String describe;
    private int discount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return String.format("Store[id=%d, userId=%s, storeName=%s, storeImage=%s, describe=%s, discount=%d]",
                id, userId, storeName, storeImage, describe, discount);
    }
}
