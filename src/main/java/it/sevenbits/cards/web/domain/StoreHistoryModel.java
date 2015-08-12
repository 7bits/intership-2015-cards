package it.sevenbits.cards.web.domain;


public class StoreHistoryModel {
    private Long id;
    private String storeName;
    private String description;


    public StoreHistoryModel(Long id, String storeName, String description) {
        this.id = id;
        this.storeName = storeName;
        this.description = description;
    }

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
        return String.format("StoryHistoryModel[id=%d, storeName=%s, description=%s]",
                id, storeName, description);
    }
}


