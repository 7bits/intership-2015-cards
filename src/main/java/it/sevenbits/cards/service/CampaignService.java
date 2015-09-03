package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.core.repository.CampaignRepository;
import it.sevenbits.cards.web.domain.forms.AddCampaignForm;
import it.sevenbits.cards.web.domain.models.CampaignModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService {
    @Autowired

    @Qualifier(value = "campaignPersistRepository")
    private CampaignRepository campaignRepository;


    public Campaign save(final AddCampaignForm addCampaignForm, String storeName) throws ServiceException {
        final Campaign campaign = new Campaign();
        campaign.setName(addCampaignForm.getName());
        campaign.setDescription(addCampaignForm.getDescription());
        campaign.setPercent(Integer.parseInt(addCampaignForm.getPercent()));
        campaign.setEnabled(true);
        campaign.setStoreName(storeName);
        campaign.setBackerPercent(Integer.parseInt(addCampaignForm.getBackerPercent()));
        try {
            campaignRepository.save(campaign);
            return campaign;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving campaign: " + e.getMessage(), e);
        }
    }

    public List<CampaignModel> findAllActive(String storeName) throws ServiceException {
        try {
            List<Campaign> campaigns = campaignRepository.findAllActive(storeName);
            List<CampaignModel> models = new ArrayList<>(campaigns.size());
            for (Campaign c: campaigns) {
                models.add(new CampaignModel(
                        c.getId(),
                        c.getName(),
                        c.getDescription(),
                        Integer.toString(c.getPercent()),
                        Integer.toString(c.getBackerPercent())
                ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }

    public List<CampaignModel> findAllNotActive(String storeName) throws ServiceException {
        try {
            List<Campaign> campaigns = campaignRepository.findAllNotActive(storeName);
            List<CampaignModel> models = new ArrayList<>(campaigns.size());
            for (Campaign c: campaigns) {
                models.add(new CampaignModel(
                        c.getId(),
                        c.getName(),
                        c.getDescription(),
                        Integer.toString(c.getPercent()),
                        Integer.toString(c.getBackerPercent())
                ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }
    public void changeCampaignEnableStatus(String id) throws ServiceException{
        try {
            campaignRepository.changeCampaignEnableStatus(Long.parseLong(id));
        } catch (Exception e) {
            throw new ServiceException("An error occurred while changing campaign status: " + e.getMessage(), e);
        }
    }
}