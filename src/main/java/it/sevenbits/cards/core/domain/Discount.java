package it.sevenbits.cards.core.domain;

import java.io.Serializable;

public class Discount implements Serializable{
    private Long id;
    private String key;
    private String uin;
    private Boolean isHidden;
    private String userId;
    private String storeName;
    private String description;

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

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return String.format("Discount[id=%d, key=%s, uid=%s, isHidden=%b, userId=%s, storeName=%s, description=%s]",
                id, key, uin, isHidden, userId, storeName, description);
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
}
