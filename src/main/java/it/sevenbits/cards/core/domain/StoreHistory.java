package it.sevenbits.cards.core.domain;


import java.sql.Timestamp;
import java.util.Date;

public class StoreHistory {
    private Long id;
    private Long storeId;
    private String action;
    private String subject;
    private Timestamp createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("StoreHistory[id=%d, storeId=%d, action=%s, subject=%s, createdAt=%s]",
                id, storeId, action, subject, createdAt.toString());
    }
}


