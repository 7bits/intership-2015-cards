package it.sevenbits.cards.web.domain;


import java.util.Date;

public class StoreHistoryModel {
    private Long id;
    private String storeName;
    private String description;
    private Date createdAt;


    public StoreHistoryModel(Long id, String storeName, String description, Date createdAt) {
        this.id = id;
        this.storeName = storeName;
        this.description = description;
        this.createdAt = createdAt;
    }

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

    @Override
    public String toString() {
        return String.format("StoryHistoryModel[id=%d, storeName=%s, description=%s, createdAt=%s]",
                id, storeName, description, createdAt.toString());
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}


