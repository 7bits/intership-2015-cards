package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.core.repository.CampaignRepository;
import it.sevenbits.cards.core.repository.CampaignRepository;
import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.web.domain.*;
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


    public Campaign save(final AddCampaignForm form, String storeName) throws ServiceException {
        final Campaign campaign = new Campaign();
        campaign.setName(form.getName());
        campaign.setDescription(form.getDescription());
        campaign.setPercent(Integer.parseInt(form.getPercent()));
        campaign.setEnabled(true);
        campaign.setStoreName(storeName);
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
                        Integer.toString(c.getPercent())
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
                        Integer.toString(c.getPercent())
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