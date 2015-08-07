package it.sevenbits.cards.web.domain;

public class CampaignModel {
    private Long id;
    private String name;
    private String description;
    private String percent;

    public CampaignModel(Long id, String name, String description, String percent){
        this.id = id;
        this.name = name;
        this.description = description;
        this.percent = percent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return String.format("id=%d, name=%s, description=%s, percent=%s]",
               id, name, description, percent);
    }

}
