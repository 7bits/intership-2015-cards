package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.StoreHistory;
import it.sevenbits.cards.core.repository.StoreHistoryRepository;
import it.sevenbits.cards.web.domain.models.StoreHistoryModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreHistoryService {

    @Autowired
    @Qualifier(value = "storeHistoryPersistRepository")
    private StoreHistoryRepository storeHistoryRepository;

    private Logger LOG = Logger.getLogger(StoreHistoryService.class);

    public List<StoreHistoryModel> findAll(Long storeId) throws ServiceException {
        try {
            List<StoreHistory> history = storeHistoryRepository.findAll(storeId);
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
    public void save(Long storeId, String description) throws ServiceException {
        StoreHistory storeHistory = new StoreHistory();
        storeHistory.setStoreId(storeId);
        storeHistory.setDescription(description);
        try {
            storeHistoryRepository.save(storeHistory);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving store history: " + e.getMessage(), e);
        }
    }
}

