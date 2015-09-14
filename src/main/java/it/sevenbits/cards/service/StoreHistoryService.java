package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.StoreHistory;
import it.sevenbits.cards.core.repository.StoreHistoryRepository;
import it.sevenbits.cards.web.domain.forms.AddCampaignForm;
import it.sevenbits.cards.web.domain.forms.DiscountByCampaignForm;
import it.sevenbits.cards.web.domain.forms.KeyForm;
import it.sevenbits.cards.web.domain.models.StoreHistoryModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreHistoryService {

    @Autowired
    @Qualifier(value = "storeHistoryPersistRepository")
    private StoreHistoryRepository storeHistoryRepository;

    private Logger LOG = Logger.getLogger(StoreHistoryService.class);

    public List<StoreHistoryModel> findAll() throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        try {
            List<StoreHistory> history = storeHistoryRepository.findAll(email);
            List<StoreHistoryModel> models = new ArrayList<>(history.size());
            for (StoreHistory h: history) {
                models.add(new StoreHistoryModel(
                        h.getId(),
                        h.getStoreId(),
                        h.getDescription(),
                        h.getCreatedAt()
                ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving history: " + e.getMessage(), e);
        }
    }
    public void save(AddCampaignForm addCampaignForm) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        StoreHistory storeHistory = new StoreHistory();
        String description =
                addCampaignForm.getName() + " " +
                addCampaignForm.getDescription() + " " +
                addCampaignForm.getPercent() + " " +
                addCampaignForm.getBackerPercent();
        storeHistory.setDescription(description);
        try {
            storeHistoryRepository.save(storeHistory, email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving store history: " + e.getMessage(), e);
        }
    }
    public void save(KeyForm keyForm) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        StoreHistory storeHistory = new StoreHistory();
        String description =
                keyForm.getKey();
        storeHistory.setDescription(description);
        try {
            storeHistoryRepository.save(storeHistory, email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving store history: " + e.getMessage(), e);
        }
    }
    public void save(DiscountByCampaignForm discountByCampaignForm) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        StoreHistory storeHistory = new StoreHistory();
        String description =
                discountByCampaignForm.getName() + " " +
                        discountByCampaignForm.getDescription() + " " +
                        discountByCampaignForm.getPercent() + " " +
                        discountByCampaignForm.getBackerPercent();
        storeHistory.setDescription(description);
        try {
            storeHistoryRepository.save(storeHistory, email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving store history: " + e.getMessage(), e);
        }
    }
}

