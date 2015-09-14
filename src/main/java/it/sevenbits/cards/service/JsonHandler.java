package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.service.validators.AddCampaignFormValidator;
import it.sevenbits.cards.service.validators.DiscountByCampaignFormValidator;
import it.sevenbits.cards.service.validators.IdValidator;
import it.sevenbits.cards.service.validators.KeyFormValidator;
import it.sevenbits.cards.web.domain.JsonResponse;
import it.sevenbits.cards.web.domain.forms.AddCampaignForm;
import it.sevenbits.cards.web.domain.forms.DiscountByCampaignForm;
import it.sevenbits.cards.web.domain.forms.KeyForm;
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
    private KeyFormValidator keyFormValidator;

    @Autowired
    private DiscountByCampaignFormValidator discountByCampaignFormValidator;

    @Autowired
    private StoreHistoryService storeHistoryService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private DiscountService discountService;

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
    public JsonResponse useDiscount(KeyForm keyForm) throws ServiceException {
        JsonResponse res = new JsonResponse();
        final Map<String, String> errors = keyFormValidator.validate(keyForm);
        if (errors.size() == 0) {
            discountService.createFeedbackDiscountAfterUse(keyForm);
            discountService.delete(keyForm);
            storeHistoryService.save(keyForm);
            res.setStatus("SUCCESS");
            res.setResult(null);
        } else {
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }
    public JsonResponse createDiscountByCampaign(DiscountByCampaignForm discountByCampaignForm) throws ServiceException {
        JsonResponse res = new JsonResponse();
        final Map<String, String> errors = discountByCampaignFormValidator.validate(discountByCampaignForm);
        if (errors.size() == 0) {
            discountService.createByCampaign(discountByCampaignForm);
//            Discount discount = discountService.findDiscountByUin(generatedUin);
//            final Map<String, String> exist = emailExistValidator.validate(discountByCampaignForm.getEmail());
//            if (exist.size() != 0) {
//                notificationService.notificateCreate(discountByCampaignForm, discount.getId());
//            } else {
//                notificationService.notificateCreateIfExist(discountByCampaignForm, discount.getId());
//            }
            storeHistoryService.save(discountByCampaignForm);
            res.setStatus("SUCCESS");
            res.setResult(null);
        } else {
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }
}
