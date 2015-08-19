package it.sevenbits.cards.web.domain;

public class DiscountModel {
    private Long id;
    private String key;
    private String uin;
    private Boolean isHidden;
    private String userId;
    private String storeName;
    private String description;
    private String storeImage;
    private String backerPercent;
    private String backerUserId;

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

    public DiscountModel(Long id, String key, String uin, Boolean isHidden, String userId, String storeName, String description, String storeImage, String backerPercent, String backerUserId) {
        this.id = id;
        this.key = key;
        this.uin = uin;
        this.isHidden = isHidden;
        this.userId = userId;
        this.storeName = storeName;
        this.description = description;
        this.storeImage = storeImage;
        this.backerPercent = backerPercent;
        this.backerUserId = backerUserId;
    }

    @Override
    public String toString() {
        return String.format("Discount[id=%d, key=%s, uid=%s, isHidden=%b, userId=%s, storeName=%s, description=%s. storeImage=%s, backerPercent=%s, backerUserId=%s]",
                id, key, uin, isHidden, userId, storeName,description, storeImage, backerPercent, backerUserId);
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

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getBackerPercent() {
        return backerPercent;
    }

    public void setBackerPercent(String backerPercent) {
        this.backerPercent = backerPercent;
    }

    public String getBackerUserId() {
        return backerUserId;
    }

    public void setBackerUserId(String backerUserId) {
        this.backerUserId = backerUserId;
    }
}