package it.sevenbits.cards.core.domain;

import java.io.Serializable;

public class Discount implements Serializable{
    private Long id;
    private String key;
    private String uin;
    private Boolean isHidden;
    private String userId;

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

    public String getUserID() {
        return userId;
    }

    public void setUserID(String userID) {
        this.userId = userID;
    }

    @Override
    public String toString() {
        return String.format("Discount[id=%d, key=%s, uid=%s, isHidden=%b, userId=%s]",
                id, key, uin, isHidden, userId);
    }
}
