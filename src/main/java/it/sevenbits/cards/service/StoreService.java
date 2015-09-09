package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.Store;
import it.sevenbits.cards.core.repository.StoreRepository;
import it.sevenbits.cards.web.domain.forms.AddStoreForm;
import it.sevenbits.cards.web.domain.forms.SaveStoreForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    @Autowired

    @Qualifier(value = "storePersistRepository")
    private StoreRepository repository;

    public void save(AddStoreForm addStoreForm) throws ServiceException {
        Store store = new Store();
        store.setStoreName(addStoreForm.getStoreName());
        store.setStoreImage(addStoreForm.getStoreImage());
        try {
            repository.save(store);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving store: " + e.getMessage(), e);
        }
    }

    public Store findByUserId(String userId) throws ServiceException {
        try {
            return repository.findByUserId(userId);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding store by user id: " + e.getMessage(), e);
        }
    }
}