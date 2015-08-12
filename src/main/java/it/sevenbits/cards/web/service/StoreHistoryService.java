package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.domain.StoreHistory;
import it.sevenbits.cards.core.repository.StoreHistoryRepository;
import it.sevenbits.cards.core.repository.StoreRepository;
import it.sevenbits.cards.web.domain.StoreHistoryModel;
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

    public List<StoreHistoryModel> findAll(String storeName) throws ServiceException {
        try {
            List<StoreHistory> history = storeHistoryRepository.findAll(storeName);
            List<StoreHistoryModel> models = new ArrayList<>(history.size());
            for (StoreHistory h: history) {
                models.add(new StoreHistoryModel(
                        h.getId(),
                        h.getStoreName(),
                        h.getDescription()
                ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving history: " + e.getMessage(), e);
        }
    }
}

