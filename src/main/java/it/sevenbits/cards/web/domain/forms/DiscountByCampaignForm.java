package it.sevenbits.cards.web.domain.forms;

public class DiscountByCampaignForm {
    private Long campaignId;
    private String email;

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("DiscountByCampaignForm[campaignId=%d, email=%s]", campaignId, email);
    }

}
