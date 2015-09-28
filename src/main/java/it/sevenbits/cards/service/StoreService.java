package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.Store;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.StoreRepository;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.web.domain.forms.AddStoreForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    @Qualifier(value = "storePersistRepository")
    private StoreRepository storeRepository;

    @Autowired
    private UserService userService;

    public void createStore(AddStoreForm addStoreForm) throws ServiceException {
        Store store = new Store();
        User user = new User();
        try {
            user = userService.findByEmail(addStoreForm.getEmail());
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding user by email: " + e.getMessage(), e);
        }
        store.setUserId(user.getId());
        store.setStoreName(addStoreForm.getStoreName());
        store.setStoreImage(addStoreForm.getStoreImage());
        try {
            storeRepository.save(store);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving store: " + e.getMessage(), e);
        }
        try {
            userService.changeUserRoleByEmail("ROLE_STORE", addStoreForm.getEmail());
        } catch (Exception e) {
            throw new ServiceException("An error occurred while changing user role by email: " + e.getMessage(), e);
        }
    }
}