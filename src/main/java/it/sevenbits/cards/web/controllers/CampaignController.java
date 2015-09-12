package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.core.domain.Campaign;
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

@Secured("ROLE_STORE")
@Controller
public class CampaignController {

    @Autowired
    private ModelBuilder modelBuilder;

    @Autowired
    private JsonHandler jsonHandler;

    private Logger LOG = Logger.getLogger(CampaignController.class);

    //Add campaign
    @RequestMapping(value = "/add_campaign", method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse saveCampaign(@ModelAttribute AddCampaignForm addCampaignForm) throws ServiceException {
        return jsonHandler.saveCampaignAndSaveActionHistory(addCampaignForm);
    }

    @RequestMapping(value = "/change_campaign_status/", method = RequestMethod.POST)
    public String deactivateCampaign(@RequestParam String id) throws ServiceException {
        return jsonHandler.changeCampaignEnableStatus(id);
    }
}
