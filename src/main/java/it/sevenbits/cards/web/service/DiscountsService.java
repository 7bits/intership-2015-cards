package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.repository.DiscountsRepository;
import it.sevenbits.cards.web.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountsService {
    @Autowired

    @Qualifier(value = "discountPersistRepository")
    private DiscountsRepository repository;

    public void save(final DiscountForm form) throws ServiceException {
        final Discount discount = new Discount();
        discount.setKey(form.getKey());
        discount.setUin(form.getUin());
        discount.setIsHidden(form.getIsHidden());
        discount.setUserId(form.getUserId());
        discount.setStoreName(form.getStoreName());
        discount.setDescription(form.getDescription());
        try {
            repository.save(discount);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }

    public List<DiscountModel> findAll() throws ServiceException {
        try {
            List<Discount> discounts = repository.findAll();
            List<DiscountModel> models = new ArrayList<>(discounts.size());
            for (Discount d: discounts) {
                models.add(new DiscountModel(
                        d.getId(),
                        d.getKey(),
                        d.getUin(),
                        d.getIsHidden(),
                        d.getUserId(),
                        d.getStoreName(),
                        d.getDescription()
                        ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }

    public List<DiscountModel> findAllDiscountsToUse() throws ServiceException {
        try {
            List<Discount> discounts = repository.findAllDiscountsToUse();
            List<DiscountModel> models = new ArrayList<>(discounts.size());
            for (Discount d: discounts) {
                models.add(new DiscountModel(
                        d.getId(),
                        d.getKey(),
                        d.getUin(),
                        d.getIsHidden(),
                        d.getUserId(),
                        d.getStoreName(),
                        d.getDescription()
                ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }
    public List<DiscountModel> findAllDiscountsToSend() throws ServiceException {
        try {
            List<Discount> discounts = repository.findAllDiscountsToSend();
            List<DiscountModel> models = new ArrayList<>(discounts.size());
            for (Discount d: discounts) {
                models.add(new DiscountModel(
                        d.getId(),
                        d.getKey(),
                        d.getUin(),
                        d.getIsHidden(),
                        d.getUserId(),
                        d.getStoreName(),
                        d.getDescription()
                ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }

    public void delete(final DiscountForm discountForm) throws ServiceException {
        final Discount discount = new Discount();
        discount.setUin(discountForm.getUin());
        try {
            repository.delete(discount);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while deleting discount: " + e.getMessage(), e);
        }
    }
    public List<DiscountModel> findUserId(Discount discount) throws ServiceException {
        try {
            List<Discount> discounts = repository.findUserId(discount);
            List<DiscountModel> models = new ArrayList<>(discounts.size());
            for (Discount d : discounts) {
                models.add(new DiscountModel(
                        d.getId(),
                        d.getKey(),
                        d.getUin(),
                        d.getIsHidden(),
                        d.getUserId(),
                        d.getStoreName(),
                        d.getDescription()
                ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }
    public void changeUserId(final DiscountForm discountForm) throws ServiceException {
        Discount discount = new Discount();
        discount.setUin(discountForm.getUin());
        try {
            repository.changeUserId(discount);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while deleting discount: " + e.getMessage(), e);
        }
    }
    public void sendDiscount(final DiscountForm discountForm) throws ServiceException {
        Discount discount = new Discount();
        discount.setUin(discountForm.getUin());
        discount.setUserId(discountForm.getUserId());
        try {
            repository.sendDiscount(discount);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while sending discount: " + e.getMessage(), e);
        }
    }
}