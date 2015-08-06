package it.sevenbits.cards.core.domain;

import java.io.Serializable;

public class Campaign implements Serializable {
    private Long id;
    private String storeName;
    private String name;
    private String description;
    private int percent;
    private Boolean enabled;

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

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return String.format("Campaign[id=%d, storeName=%s, name=%s, description=%s, percent=%d, enabled=%b]",
                id, storeName, name, description, percent, enabled);
    }
}
