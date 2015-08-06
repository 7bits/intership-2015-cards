package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.web.domain.AddCampaignForm;
import it.sevenbits.cards.web.domain.JsonResponse;
import it.sevenbits.cards.web.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    private Logger LOG = Logger.getLogger(HomeController.class);

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
            campaignService.save(addCampaignForm, storeName);
            res.setStatus("SUCCESS");
            res.setResult(null);
        }else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }
}
