package it.sevenbits.cards.web.domain.models;


import java.sql.Timestamp;
import java.util.Date;

public class StoreHistoryModel {
    private Long id;
    private Long storeId;
    private String description;
    private Date createdAt;


    public StoreHistoryModel(Long id, Long storeId, String description, Date createdAt) {
        this.id = id;
        this.storeId = storeId;
        this.description = description;
        this.createdAt = createdAt;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("StoryHistoryModel[id=%d,storeId=%d, description=%s, createdAt=%s]",
                id, storeId, description, createdAt.toString());
    }
}


