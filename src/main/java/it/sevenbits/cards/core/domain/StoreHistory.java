package it.sevenbits.cards.core.domain;


import java.sql.Timestamp;
import java.util.Date;

public class StoreHistory {
    private Long id;
    private String storeName;
    private String description;
    private Timestamp createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("StoreHistory[id=%d, storeName=%s, description=%s, createdAt=%s]",
                id, storeName, description, createdAt.toString());
    }


}


