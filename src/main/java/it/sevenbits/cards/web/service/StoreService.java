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
    private StoreRepository storeRepository;

    public void save(SaveStoreForm saveStoreForm) throws ServiceException {
        Store store = new Store();
        store.setUserId(saveStoreForm.getUserId());
        store.setStoreName(saveStoreForm.getStoreName());
        store.setStoreImage(saveStoreForm.getStoreImage());
        try {
            storeRepository.save(store);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving store: " + e.getMessage(), e);
        }
    }
}