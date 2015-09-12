package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.service.validators.AddCampaignFormValidator;
import it.sevenbits.cards.service.validators.IdValidator;
import it.sevenbits.cards.web.domain.JsonResponse;
import it.sevenbits.cards.web.domain.forms.AddCampaignForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JsonHandler {

    @Autowired
    private IdValidator idValidator;

    @Autowired
    private AddCampaignFormValidator addCampaignFormValidator;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private StoreHistoryService storeHistoryService;

    public String changeCampaignEnableStatus(String id) throws ServiceException {
        final Map<String, String> errors = idValidator.validate(id);
        if (errors.size() == 0) {
            campaignService.changeCampaignEnableStatus(id);
        }
        return "redirect:/store_area";
    }

    public JsonResponse saveCampaignAndSaveActionHistory(AddCampaignForm addCampaignForm) throws ServiceException{
        JsonResponse res = new JsonResponse();
        final Map<String, String> errors = addCampaignFormValidator.validate(addCampaignForm);
        if (errors.size() == 0)
        {
            Campaign campaign = campaignService.save(addCampaignForm);
            storeHistoryService.save(addCampaignForm);
            res.setStatus("SUCCESS");
            res.setResult(campaign);
        } else {
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }
}
