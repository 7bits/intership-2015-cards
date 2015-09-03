package it.sevenbits.cards.web.domain.forms;

public class AddStoreForm {
    private String email;
    private String storeName;
    private String storeImage;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return String.format("StoreuserId=%s, storeName=%s, storeImage=%s]",
                email, storeName, storeImage);
    }
}
