package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.domain.Store;
import it.sevenbits.cards.core.repository.StoreRepository;
import it.sevenbits.cards.web.domain.SaveStoreForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    @Autowired

    @Qualifier(value = "storePersistRepository")
    private StoreRepository repository;

    public void save(SaveStoreForm saveStoreForm) throws ServiceException {
        Store store = new Store();
        store.setUserId(saveStoreForm.getUserId());
        store.setStoreName(saveStoreForm.getStoreName());
        store.setStoreImage(saveStoreForm.getStoreImage());
        try {
            repository.save(store);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving store: " + e.getMessage(), e);
        }
    }
    public String findStoreNameByUserId(String userId) throws ServiceException {
        try {
            return repository.findStoreNameByUserId(userId);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding UserId by User Name discount: " + e.getMessage(), e);
        }
    }
}