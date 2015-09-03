package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.core.domain.StoreHistory;
import it.sevenbits.cards.web.domain.forms.AddCampaignForm;
import it.sevenbits.cards.web.domain.models.CampaignModel;
import it.sevenbits.cards.web.domain.JsonResponse;
import it.sevenbits.cards.service.*;
import it.sevenbits.cards.service.validators.AddCampaignFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class CampaignController {

    @Autowired
    private AddCampaignFormValidator addCampaignFormValidator;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreHistoryService storeHistoryService;

    private Logger LOG = Logger.getLogger(CampaignController.class);

    //Add campaign
    @Secured("ROLE_STORE")
    @RequestMapping(value = "/add_campaign", method=RequestMethod.GET)
    public String addCampaign(Model model) throws ServiceException{
        model.addAttribute("addCampaignForm", new AddCampaignForm());
        return "home/add_campaign";
    }

    //Save campaign
    @Secured("ROLE_STORE")
    @RequestMapping(value = "/add_campaign", method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse saveCampaign(@ModelAttribute AddCampaignForm addCampaignForm) throws ServiceException {
        JsonResponse res = new JsonResponse();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        final Map<String, String> errors = addCampaignFormValidator.validate(addCampaignForm);
        if (errors.size() == 0) {
            res.setStatus("SUCCESS");
            Campaign campaign = campaignService.save(addCampaignForm, storeName);
            res.setResult(campaign);
            StoreHistory storeHistory = new StoreHistory();
            storeHistory.setStoreName(storeName);
            storeHistory.setDescription("Создана кампания " + addCampaignForm.getName() + " " + " с описанием " + addCampaignForm.getDescription() + " " + " с скидкой " + addCampaignForm.getPercent() + "%");
            storeHistoryService.save(storeHistory);
        }else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }
    //Show Campaigns
    @Secured("ROLE_STORE")
    @RequestMapping(value = "/campaigns", method = RequestMethod.GET)
    public String showAll(final Model model) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        List<CampaignModel> campaigns = campaignService.findAllActive(storeName);
        //Should be use for Store History
        int campaignsLenght = 10;
        if(campaigns.size()>campaignsLenght){
            campaigns = campaigns.subList(campaigns.size()-campaignsLenght, campaigns.size());
        }
        model.addAttribute("campaigns", campaigns);
        //
        model.addAttribute("activeCampaigns", campaignService.findAllActive(storeName));
        model.addAttribute("notActiveCampaigns", campaignService.findAllNotActive(storeName));
        return "home/campaigns";
    }
    @Secured("ROLE_STORE")
    @RequestMapping(value = "/change_campaign_status/", method = RequestMethod.POST)
    public String deactivateCampaign(@RequestParam Long id) throws ServiceException {
        campaignService.changeCampaignEnableStatus(id.toString());
        return "redirect:/store_area";
    }
}
