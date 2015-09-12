package it.sevenbits.cards.core.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Campaign implements Serializable {
    //Campaign
    private Long id;
    private Long storeId;
    private String name;
    private String description;
    private Long percent;
    private Long backerPercent;
    private Boolean enabled;
    private Timestamp createdAt;
    //Store
    private String storeName;
    private String storeImage;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPercent() {
        return percent;
    }

    public void setPercent(Long percent) {
        this.percent = percent;
    }

    public Long getBackerPercent() {
        return backerPercent;
    }

    public void setBackerPercent(Long backerPercent) {
        this.backerPercent = backerPercent;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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
        return String.format("Campaign[id=%d, storeId=%d, name=%s, description=%s, percent=%d, backerPercent=%d, enabled=%b, createdAt=%s, storeName=%s, storeImage=%s]",
                id, storeId, name, description, percent, backerPercent, enabled, createdAt.toString(), storeName, storeImage);
    }

}
