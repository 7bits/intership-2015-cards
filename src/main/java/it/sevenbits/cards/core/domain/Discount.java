package it.sevenbits.cards.core.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Discount implements Serializable {
    private Long id;
    private String key;
    private Boolean isHidden;
    private Long userId;
    private Long backerUserId;
    private Long campaignId;
    private Boolean deleted;
    private String hash;
    private Timestamp createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBackerUserId() {
        return backerUserId;
    }

    public void setBackerUserId(Long backerUserId) {
        this.backerUserId = backerUserId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("Discount[id=%d, key=%s, isHidden=%b, userId=%d, backerUserId=%d, campaignId=%d, deleted=%b, hash=%s, createdAt=%s]",
                id, key, isHidden, userId, backerUserId, campaignId, deleted, hash, createdAt.toString());
    }
}

