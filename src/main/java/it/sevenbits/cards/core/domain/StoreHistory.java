package it.sevenbits.cards.core.domain;


public class StoreHistory {
    private Long id;
    private String storeName;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return String.format("StoreHistory[id=%d, storeName=%s, description=%s]",
                id, storeName, description);
    }
}


