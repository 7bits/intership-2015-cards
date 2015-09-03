package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.repository.DiscountRepository;
import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.web.domain.forms.DiscountByCampaignForm;
import it.sevenbits.cards.web.domain.forms.DiscountForm;
import it.sevenbits.cards.web.domain.forms.GenerateDiscountForm;
import it.sevenbits.cards.web.domain.models.DiscountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        discount.setKey(form.getKey());
        discount.setUin(form.getUin());
        discount.setIsHidden(Boolean.parseBoolean(form.getIsHidden()));
        discount.setUserId(form.getUserId());
        discount.setStoreName(form.getStoreName());
        discount.setDescription(form.getDescription());
        try {
            discountRepository.save(discount);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }

    public List<DiscountModel> findAll() throws ServiceException {
        try {
            List<Discount> discounts = discountRepository.findAll();
            List<DiscountModel> models = new ArrayList<>(discounts.size());
            for (Discount d: discounts) {
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

    public List<DiscountModel> findAllForUse(String userName) throws ServiceException {
        try {
            List<Discount> discounts = discountRepository.findAllForUse(userName);
            List<DiscountModel> models = new ArrayList<>(discounts.size());
            for (Discount d: discounts) {
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
    public List<DiscountModel> findAllForSend(String userName) throws ServiceException {
        try {
            List<Discount> discounts = discountRepository.findAllForSend(userName);
            List<DiscountModel> models = new ArrayList<>(discounts.size());
            for (Discount d: discounts) {
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
    public void changeUserId(String uin, String userId) throws ServiceException {
        try {
            discountRepository.changeUserId(uin, userId);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while deleting discount: " + e.getMessage(), e);
        }
    }
    public void send(String userId, String uin, String email) throws ServiceException {
        if(userId == null) {
            userId = "";
        }
        try {
            discountRepository.send(userId, uin, email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while sending discount: " + e.getMessage(), e);
        }
    }
    public void generateDiscount(final GenerateDiscountForm generateDiscountForm, String generateKey, String generateUin) throws ServiceException, RepositoryException
    {
        final Discount discount = new Discount();
        discount.setKey(generateKey);
        discount.setUin(generateUin);
        discount.setIsHidden(Boolean.parseBoolean("true"));
        discount.setUserId(userRepository.findUserIdByUserName(generateDiscountForm.getUserName()));
        discount.setStoreName(generateDiscountForm.getStoreName());
        discount.setDescription(generateDiscountForm.getDescription());
        discount.setPercent(Integer.parseInt(generateDiscountForm.getDiscountPercent()));
        try {
            discountRepository.save(discount);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }
    public void createDiscountByCampaign(DiscountByCampaignForm discountByCampaignForm, String generatedKey, String generatedUin, String storeName, String storeImage) throws ServiceException, RepositoryException {
        final Discount discount = new Discount();
        String userId;
        try {
            userId = userRepository.findUserIdByUserName(discountByCampaignForm.getEmail());
        }
        catch (Exception e){
            userId="";
        }
        discount.setKey(generatedKey);
        discount.setUin(generatedUin);
        discount.setIsHidden(Boolean.parseBoolean("true"));
        discount.setUserId(userId);
        discount.setStoreName(storeName);
        discount.setDescription(discountByCampaignForm.getDescription());
        discount.setPercent(Integer.parseInt(discountByCampaignForm.getPercent()));
        discount.setStoreImage(storeImage);
        discount.setBackerPercent(Integer.parseInt(discountByCampaignForm.getBackerPercent()));
        discount.setBackerUserId(userId);
        discount.setEmail(discountByCampaignForm.getEmail());
        try {
            discountRepository.saveByAcoustics(discount);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }

    public Discount findDiscountByUin(String uin) throws ServiceException {
        try {
            return discountRepository.findDiscountByUin(uin);
        } catch (Exception e) {
            throw new ServiceException("An error while finding discount by uin: " + e.getMessage(), e);
        }
    }
    public Discount findDiscountById(Long id) throws ServiceException {
        try {
            return discountRepository.findDiscountById(id);
        } catch (Exception e) {
            throw new ServiceException("An error while finding discount by id: " + e.getMessage(), e);
        }
    }
    public void createFeedbackDiscountAfterUse(String key) throws ServiceException {
        String generatedKey = generateKeyService.random();
        String generatedUin = generateUinService.random();
        Discount oldDiscount;
        try {
            oldDiscount = discountRepository.findDiscountByKey(key);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding discount by uin: " + e.getMessage(), e);
        }
        if (oldDiscount.getBackerPercent() != 0) {
            Discount newDiscount = new Discount();
            newDiscount.setKey(generatedKey);
            newDiscount.setUin(generatedUin);
            newDiscount.setIsHidden(Boolean.parseBoolean("false"));
            newDiscount.setUserId(oldDiscount.getBackerUserId());
            newDiscount.setStoreName(oldDiscount.getStoreName());
            newDiscount.setDescription(oldDiscount.getDescription());
            newDiscount.setPercent(oldDiscount.getBackerPercent());
            newDiscount.setStoreImage(oldDiscount.getStoreImage());
            newDiscount.setBackerPercent(0);
            newDiscount.setBackerUserId("");
            try {
                discountRepository.saveByAcoustics(newDiscount);
            } catch (Exception e) {
                throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
            }
        }
    }
    public void addExistDiscountsByEmail(String email, String userId) throws ServiceException {
        try {
            discountRepository.addExistDiscountsByEmail(email, userId);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while adding exist discounts: " + e.getMessage(), e);
        }
    }

}