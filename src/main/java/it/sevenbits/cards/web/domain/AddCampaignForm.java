package it.sevenbits.cards.web.domain;

public class AddCampaignForm {
    private String name;
    private String description;
    private String percent;

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

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return String.format("name=%s, description=%s, percent=%s]",
                name, description, percent);
    }
}


