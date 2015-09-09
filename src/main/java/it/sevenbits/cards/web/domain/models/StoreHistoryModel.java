package it.sevenbits.cards.web.domain.models;


import java.sql.Timestamp;
import java.util.Date;

public class StoreHistoryModel {
    private Long id;
    private Long storeId;
    private String action;
    private String subject;
    private Date createdAt;


    public StoreHistoryModel(Long id, Long storeId, String action, String subject, Date createdAt) {
        this.id = id;
        this.storeId = storeId;
        this.action = action;
        this.subject = subject;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return String.format("StoryHistoryModel[id=%d,storeId=%d, action=%s, subject=%s, createdAt=%s]",
                id, storeId, action, subject, createdAt.toString());
    }
}


