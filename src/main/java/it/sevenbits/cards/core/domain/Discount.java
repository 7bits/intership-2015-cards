package it.sevenbits.cards.core.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Discount implements Serializable {
    private Long id;
    private String key;
    private Boolean isHidden;
    private String email;
    private String backerEmail;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBackerEmail() {
        return backerEmail;
    }

    public void setBackerEmail(String backerEmail) {
        this.backerEmail = backerEmail;
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
        return String.format("Discount[id=%d, key=%s, isHidden=%b, email=%s, backerEmail=%s, campaignId=%d, deleted=%b, hash=%s, createdAt=%s]",
                id, key, isHidden, email, backerEmail, campaignId, deleted, hash, createdAt.toString());
    }
}

