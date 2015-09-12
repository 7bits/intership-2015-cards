package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.repository.DiscountRepository;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.web.domain.forms.DiscountByCampaignForm;
import it.sevenbits.cards.web.domain.forms.DiscountForm;
import it.sevenbits.cards.web.domain.forms.GenerateDiscountForm;
import it.sevenbits.cards.web.domain.models.DiscountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountService {
    @Autowired
    @Qualifier(value = "discountPersistRepository")
    private DiscountRepository discountRepository;

    @Autowired
    @Qualifier(value = "userRepository")
    private UserRepository userRepository;

    @Autowired
    private GenerateKeyService generateKeyService;

    @Autowired
    private GenerateUinService generateUinService;

    public void save(final DiscountForm form) throws ServiceException {
        final Discount discount = new Discount();
        discount.setIsHidden(Boolean.parseBoolean(form.getIsHidden()));
        discount.setStoreName(form.getStoreName());
        discount.setDescription(form.getDescription());
        try {
            discountRepository.save(discount);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }

    public List<DiscountModel> findAllWithHiddenStatus(Boolean isHidden) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        try {
            List<Discount> discounts = discountRepository.findAllWithHiddenStatus(isHidden, email);
            List<DiscountModel> models = new ArrayList<>(discounts.size());
            for (Discount d: discounts) {
                models.add(new DiscountModel(
                        d.getId(),
                        d.getKey(),
                        d.getIsHidden(),
                        d.getUserId(),
                        d.getBackerUserId()
                ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }
    public void delete(String key, String storeName) throws ServiceException {
        try {
            discountRepository.delete(key, storeName);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while deleting discount: " + e.getMessage(), e);
        }
    }
    public List<DiscountModel> findUserId(Discount discount) throws ServiceException {
        try {
            List<Discount> discounts = discountRepository.findUserId(discount);
            List<DiscountModel> models = new ArrayList<>(discounts.size());
            for (Discount d : discounts) {
                models.add(new DiscountModel(
                        d.getId(),
                        d.getKey(),
                        d.getUin(),
                        d.getIsHidden(),
                        d.getUserId(),
                        d.getStoreName(),
                        d.getDescription(),
                        d.getStoreImage(),
                        Integer.toString(d.getPercent()),
                        Integer.toString(d.getBackerPercent()),
                        d.getBackerUserId(),
                        d.getEmail()
                ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }
}