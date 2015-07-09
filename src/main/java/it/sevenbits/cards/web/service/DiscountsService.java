package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.repository.DiscountRepository;
import it.sevenbits.cards.web.domain.DiscountForm;
import it.sevenbits.cards.web.domain.DiscountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountsService {
    @Autowired
    private DiscountRepository repository;

    public void save(final DiscountForm form) throws ServiceException {
        final Discount discount = new Discount();
        discount.setKey(form.getKey());
        discount.setUin(form.getUin());
        discount.setIsHidden(form.getIsHidden());
        discount.setUserId(form.getUserId());
        try {
            repository.save(discount);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }

    public List<DiscountModel> findAll() throws ServiceException {
        try {
            List<Discount> subscriptions = repository.findAll();
            List<DiscountModel> models = new ArrayList<>(subscriptions.size());
            for (Discount d: subscriptions) {
                models.add(new DiscountModel(
                        d.getId(),
                        d.getKey(),
                        d.getUin(),
                        d.getIsHidden(),
                        d.getUserId()
                        ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }
}