package it.sevenbits.cards.core.domain;

import java.io.Serializable;

public class Store implements Serializable {
    private Long id;
    private String userId;
    private String storeName;
    private String storeImage;
    private String description;
    private int discountPercent;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public String toString() {
        return String.format("Store[id=%d, userId=%s, storeName=%s, storeImage=%s, description=%s, discountPercent=%d]",
                id, userId, storeName, storeImage, description, discountPercent);
    }
}
