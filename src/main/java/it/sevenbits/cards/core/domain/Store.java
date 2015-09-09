package it.sevenbits.cards.core.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Store implements Serializable {
    private Long id;
    private String userId;
    private String storeName;
    private String storeImage;
    private Timestamp createdAt;

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("Store[id=%d, userId=%s, storeName=%s, storeImage=%s, createdAt=%s]",
                id, userId, storeName, storeImage, createdAt.toString());
    }
}
