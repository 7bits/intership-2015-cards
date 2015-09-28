package it.sevenbits.cards.web.domain.models;

import java.sql.Timestamp;

public class DiscountModel {
    //Discount
    private Long id;
    private String key;
    private Boolean isHidden;
    private Long userId;
    private Long backerUserId;
    private Long campaignId;
    private Boolean deleted;
    private String hash;
    private Timestamp createdAt;
    //Campaign
    private String campaignName;
    private String description;
    private int percent;
    private int backerPercent;
    //Store
    private String storeName;
    private String storeImage;

    public DiscountModel(Long id, String key, Boolean isHidden, Long userId, Long backerUserId, Long campaignId, Boolean deleted, String hash, Timestamp createdAt, String campaignName, String description, int percent, int backerPercent, String storeName, String storeImage) {
        this.id = id;
        this.key = key;
        this.isHidden = isHidden;
        this.userId = userId;
        this.backerUserId = backerUserId;
        this.campaignId = campaignId;
        this.deleted = deleted;
        this.hash = hash;
        this.createdAt = createdAt;
        this.campaignName = campaignName;
        this.description = description;
        this.percent = percent;
        this.backerPercent = backerPercent;
        this.storeName = storeName;
        this.storeImage = storeImage;
    }

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

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getBackerPercent() {
        return backerPercent;
    }

    public void setBackerPercent(int backerPercent) {
        this.backerPercent = backerPercent;
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

    @Override
    public String toString() {
        return String.format("Discount[id=%d, key=%s, isHidden=%b, userId=%d, backerUserId=%d, campaignId=%d, deleted=%b, hash=%s, createdAt=%s, , campaignName=%s, description=%s, percent=%d, backerPercent=%d, storeName=%s, storeImage=%s]",
                id, key, isHidden, userId, backerUserId, campaignId, deleted, hash, createdAt.toString(), campaignName, description, percent, backerPercent, storeName, storeImage);
    }
}