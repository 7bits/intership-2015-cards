package it.sevenbits.cards.service;

import it.sevenbits.cards.service.validators.hash.AccountActivateHashValidator;
import it.sevenbits.cards.service.validators.hash.DiscountHashValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ModelBuilder {

    @Autowired
    UserService userService;

    @Autowired
    StoreService storeService;

    @Autowired
    StoreHistoryService storeHistoryService;

    @Autowired
    CampaignService campaignService;

    @Autowired
    DiscountService discountService;

    @Autowired
    DiscountHashValidator discountHashValidator;

    @Autowired
    AccountActivateHashValidator accountActivateHashValidator;

    private Logger LOG = Logger.getLogger(ModelBuilder.class);

    public HashMap<String, Object> buildStore() throws ServiceException{
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("activeCampaigns", campaignService.findAllWithEnabledStatus(true));
        hashMap.put("notActiveCampaigns", campaignService.findAllWithEnabledStatus(false));
        hashMap.put("history", storeHistoryService.findAll());
        return hashMap;
    }

    public HashMap<String, Object> buildCustomer() throws ServiceException{
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("discountsForSend", discountService.findAllWithHiddenStatus(true));
        hashMap.put("discountsForUse", discountService.findAllWithHiddenStatus(false));
        return hashMap;
    }

    public HashMap<String, Object> buildDiscountInfo(String hash) throws ServiceException {
        HashMap<String, Object> hashMap = new HashMap<>();
        final Map<String, String> errors = discountHashValidator.validate(hash);
        if (errors.size() == 0) {
            hashMap.put("discount", discountService.findDiscountByHash(hash));
            discountService.deleteHash(hash);
        }
        return hashMap;
    }

    public HashMap<String, Object> buildRegistration() throws ServiceException{
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("enterEmail", "Введите адрес эл. почты");
        hashMap.put("enterPassword", "Введите пароль");
        hashMap.put("registration", "Регистрация");
        hashMap.put("signUp", "Зарегистрироваться");
        return hashMap;
    }

    public HashMap<String, Object> activateUser(String hash) throws ServiceException{
        HashMap<String, Object> hashMap = new HashMap<>();
        final Map<String, String> errors = accountActivateHashValidator.validate(hash);
        if (errors.size() ==0) {
            userService.activateByHash(hash);
            hashMap.put("accActivate", "Регистрация успешно завершена");
        }
        return hashMap;
    }
}
