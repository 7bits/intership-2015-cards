package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.service.validators.*;
import it.sevenbits.cards.service.validators.form.*;
import it.sevenbits.cards.web.domain.JsonResponse;
import it.sevenbits.cards.web.domain.forms.*;
import org.apache.log4j.Logger;
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
    private SendFormValidator sendFormValidator;

    @Autowired
    private RegistrationFormValidator registrationFormValidator;

    @Autowired
    private NewPasswordFormValidator newPasswordFormValidator;

    @Autowired
    private FeedbackFormValidator feedbackFormValidator;

    @Autowired
    private UserExistValidator userExistValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreHistoryService storeHistoryService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private NotificationService notificationService;

    private static final Logger LOG = Logger.getLogger(JsonHandler.class);

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
            campaignService.save(addCampaignForm);
            storeHistoryService.save(addCampaignForm);
            res.setStatus("SUCCESS");
            res.setResult(null);
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
            Discount discount = discountService.findDiscountByKey(keyForm);
            discountService.createFeedbackDiscountAfterUse(discount);
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
            final Map<String, String> error = userExistValidator.validate(discountByCampaignForm.getEmail());
            if (error.size() != 0) {
                userService.createUser(discountByCampaignForm.getEmail());
            }
            discountService.createByCampaign(discountByCampaignForm);
            storeHistoryService.save(discountByCampaignForm);
            res.setStatus("SUCCESS");
            res.setResult(null);
        } else {
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }
    public JsonResponse sendDiscount(SendForm sendForm) throws ServiceException{
        JsonResponse res = new JsonResponse();
        final Map<String, String> errors = sendFormValidator.validate(sendForm);
        if (errors.size() == 0) {
            final Map<String, String> error = userExistValidator.validate(sendForm.getEmail());
            if (error.size() != 0) {
                userService.createUser(sendForm.getEmail());
            }
            discountService.changeDiscountOwnerByForm(sendForm);
            res.setStatus("SUCCESS");
            res.setResult(null);
        } else {
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }
    public JsonResponse registerUser(RegistrationForm registrationForm) throws ServiceException{
        final Map<String, String> errors = registrationFormValidator.validate(registrationForm);
        JsonResponse res = new JsonResponse();
        if (errors.size() == 0) {
            final Map<String, String> error = userExistValidator.validate(registrationForm.getEmail());
            if (error.size() != 0) {
                userService.createUser(registrationForm.getEmail());
            }
            userService.updateUser(registrationForm);
            notificationService.sendActivation(userService.findByEmail(registrationForm.getEmail()));
            res.setStatus("SUCCESS");
            res.setResult(null);
        } else {
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return  res;
    }

    public JsonResponse newPassword(NewPasswordForm newPasswordForm) throws ServiceException{
        final Map<String, String> errors = newPasswordFormValidator.validate(newPasswordForm);
        JsonResponse res = new JsonResponse();
        if (errors.size() == 0) {
            userService.restorePassword(newPasswordForm);
            res.setStatus("SUCCESS");
            res.setResult(null);
        }else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return  res;
    }

    public JsonResponse feedback(FeedbackForm feedbackForm) throws ServiceException{
        final Map<String, String> errors = feedbackFormValidator.validate(feedbackForm);
        JsonResponse res = new JsonResponse();
        if (errors.size() == 0) {
            notificationService.sendFeedback(feedbackForm);
            res.setStatus("SUCCESS");
            res.setResult(null);
        }else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }
}
