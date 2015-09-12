package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.domain.StoreHistory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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
        hashMap.put("discountsForUse", discountService.findAllWithHiddenStatus(true));
        hashMap.put("discountsForSend", discountService.findAllWithHiddenStatus(false));
        return hashMap;
    }
}
