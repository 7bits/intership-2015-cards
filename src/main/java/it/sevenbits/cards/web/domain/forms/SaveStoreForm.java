package it.sevenbits.cards.web.domain.forms;

public class SaveStoreForm {
    private String userId;
    private String storeName;
    private String storeImage;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        return String.format("Store[userId=%s, storeName=%s, storeImage=%s]",
                userId, storeName, storeImage);
    }
}
