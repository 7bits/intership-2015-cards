package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.service.*;
import it.sevenbits.cards.service.validators.IdFormValidator;
import it.sevenbits.cards.web.domain.forms.IdForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private StoreHistoryService storeHistoryService;

    @Autowired
    private IdFormValidator idFormValidator;

    //Store Area Get Method
    @RequestMapping(value = "/store_area", method = RequestMethod.GET)
    public String storePageNew(final Model model) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        model.addAttribute("activeCampaigns", campaignService.findAllActive(storeName));
        model.addAttribute("notActiveCampaigns", campaignService.findAllNotActive(storeName));
        model.addAttribute("history", storeHistoryService.findAll(storeName));
        model.addAttribute("storeImage", storeService.findStoreImageByStoreName(storeName));
        return "home/store_area";
    }

    //Store Area Post Method
    @RequestMapping(value = "/store_area", method = RequestMethod.POST)
    public String storeAreaPost(final Model model) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        model.addAttribute("activeCampaigns", campaignService.findAllActive(storeName));
        model.addAttribute("notActiveCampaigns", campaignService.findAllNotActive(storeName));
        model.addAttribute("history", storeHistoryService.findAll(storeName));
        model.addAttribute("storeImage", storeService.findStoreImageByStoreName(storeName));
        return "home/store_area";
    }

    //History of Store's actions
    @RequestMapping(value = "/store_history", method = RequestMethod.GET)
    public String storeHistory(final Model model) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        model.addAttribute("history", storeHistoryService.findAll(storeName));
        return "home/store_history";
    }

    //Change campaign status from active to inactive and back
    @RequestMapping(value = "/change_campaign_status", method = RequestMethod.POST)
    public String changeCampaignEnableStatus(@ModelAttribute IdForm idForm) throws ServiceException{
        final Map<String, String> errors = idFormValidator.validate(idForm.getId());
        if(errors.size() == 0){
            campaignService.changeCampaignEnableStatus(idForm.getId());
        }
        return "redirect:/store_area";
    }
}
