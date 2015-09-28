package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.repository.DiscountRepository;
import it.sevenbits.cards.validation.Sha;
import it.sevenbits.cards.web.domain.forms.DiscountByCampaignForm;
import it.sevenbits.cards.web.domain.forms.KeyForm;
import it.sevenbits.cards.web.domain.forms.SendForm;
import it.sevenbits.cards.web.domain.models.DiscountModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountService {

    @Autowired
    @Qualifier(value = "discountPersistRepository")
    private DiscountRepository discountRepository;

    @Autowired
    private GenerateKeyService generateKeyService;

    @Autowired
    private NotificationService notificationService;

    private static final Logger LOG = Logger.getLogger(DiscountService.class);

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
                                d.getBackerUserId(),
                                d.getCampaignId(),
                                d.getDeleted(),
                                d.getHash(),
                                d.getCreatedAt(),
                                d.getCampaignName(),
                                d.getDescription(),
                                d.getPercent(),
                                d.getBackerPercent(),
                                d.getStoreName(),
                                d.getStoreImage()
                        ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }

    public Discount findDiscountByHash(String hash) throws ServiceException {
        try {
            return discountRepository.findDiscountByHash(hash);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding discount by hash: " + e.getMessage(), e);
        }
    }

    public void deleteHash(String hash) throws ServiceException{
        try{
            discountRepository.deleteHash(hash);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while deleting hash from discount by hash: " + e.getMessage(), e);
        }
    }

    public Discount findDiscountByKey(KeyForm keyForm) throws ServiceException{
        try{
            return discountRepository.findDiscountByKey(keyForm.getKey());
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding discount by key: " + e.getMessage(), e);
        }
    }

    public void delete(KeyForm keyForm) throws ServiceException{
        try{
            discountRepository.delete(keyForm.getKey());
        } catch (Exception e) {
            throw new ServiceException("An error occurred while deleting discount by key: " + e.getMessage(), e);
        }
    }

    public void createFeedbackDiscountAfterUse(Discount discount) throws ServiceException{
        Long adminId = new Long(1);
        if(!discount.getBackerUserId().equals(adminId)){
            Discount backerDiscount = new Discount();
            backerDiscount.setKey(generateKeyService.random());
            backerDiscount.setUserId(discount.getBackerUserId());
            backerDiscount.setCampaignId(discount.getCampaignId());
            backerDiscount.setBackerUserId(adminId);
            backerDiscount.setIsHidden(false);
            try{
                discountRepository.createFeedbackDiscountAfterUse(backerDiscount);
            } catch (Exception e) {
                throw new ServiceException("An error occurred while creating feedback discount after use: " + e.getMessage(), e);
            }
        }
    }

    public void createByCampaign(DiscountByCampaignForm discountByCampaignForm) throws ServiceException{
        String hash = null;
        try{
            hash = Sha.hash256();
        }catch (NoSuchAlgorithmException e){
            throw new ServiceException("An error occurred while generating SHA for discount: " + e.getMessage(), e);
        }
        notificationService.sendInfoAboutDiscountByCampaign(discountByCampaignForm, hash);
        try{
            discountRepository.createByCampaign(generateKeyService.random(), discountByCampaignForm.getEmail(), discountByCampaignForm.getCampaignId(), hash);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while creating discount by campaign: " + e.getMessage(), e);
        }
    }

    public void changeDiscountOwnerByForm(SendForm sendForm) throws ServiceException{
        String hash = null;
        try{
            hash = Sha.hash256();
        }catch (NoSuchAlgorithmException e){
            throw new ServiceException("An error occurred while generating SHA for discount: " + e.getMessage(), e);
        }
        notificationService.notificateSend(sendForm, hash);
        try{
            discountRepository.changeDiscountOwnerByForm(sendForm.getEmail(), sendForm.getKey(), hash);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while changing discount owner: " + e.getMessage(), e);
        }
    }
    public Long findDiscountIdByMakerEmailAndKey(String email, String key) throws ServiceException{
        try{
            return discountRepository.findDiscountIdByMakerEmailAndKey(email, key);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding discount id by email and key: " + e.getMessage(), e);
        }
    }
    public Boolean findDiscountHiddenStatusByKey(String key) throws ServiceException{
        try{
            return discountRepository.findDiscountHiddenStatusByKey(key);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding discount hidden status by key: " + e.getMessage(), e);
        }
    }
    public Long findIdByHash(String hash) throws ServiceException {
        try {
            return discountRepository.findIdByHash(hash);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding discount hash by key: " + e.getMessage(), e);
        }
    }
    public void changeDiscountOwner(String hash) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        try{
            discountRepository.changeDiscountOwner(email, hash);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while changing discount owner by email and hash: " + e.getMessage(), e);
        }
    }
}