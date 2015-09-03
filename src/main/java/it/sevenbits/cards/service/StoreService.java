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

    public void save(String storeId, AddStoreForm addStoreForm) throws ServiceException {
        Store store = new Store();
        store.setUserId(storeId);
        store.setStoreName(addStoreForm.getStoreName());
        store.setStoreImage(addStoreForm.getStoreImage());
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


    public Store findStoreByUserId(String userId) throws ServiceException {
        Store store;
        try {
            store = repository.findStoreByUserId(userId);
            if (store == null) {
                store = new Store();
                store.setUserId(userId);
                store.setStoreName("unknown");
                store.setStoreImage("Empty");
            }
            return store;
        } catch (Exception e) {
            throw new ServiceException("findStoreByUserId error: " + e.getMessage(), e);
        }
    }
    public String findStoreImageByStoreName(String storeName) throws ServiceException{
        try{
            return repository.findStoreImageByStoreName(storeName);
        }catch (Exception e) {
            throw new ServiceException("findStoreImageByStoreName error: " + e.getMessage(), e);
        }
    }
}