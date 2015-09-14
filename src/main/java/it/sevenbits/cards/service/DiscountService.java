package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.repository.DiscountRepository;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.web.domain.forms.DiscountByCampaignForm;
import it.sevenbits.cards.web.domain.forms.DiscountForm;
import it.sevenbits.cards.web.domain.forms.GenerateDiscountForm;
import it.sevenbits.cards.web.domain.forms.KeyForm;
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
    private GenerateKeyService generateKeyService;

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
    public void delete(KeyForm keyForm) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        try {
            discountRepository.delete(keyForm.getKey(), email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while deleting discount: " + e.getMessage(), e);
        }
    }
    public void createByCampaign(DiscountByCampaignForm discountByCampaignForm) throws ServiceException {
        try {
            discountRepository.createByCampaign(generateKeyService.random(), discountByCampaignForm.getEmail(), discountByCampaignForm.getCampaignId());
        } catch (Exception e) {
            throw new ServiceException("An error occurred while deleting discount: " + e.getMessage(), e);
        }
    }
}