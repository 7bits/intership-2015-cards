package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.service.*;
import it.sevenbits.cards.service.validators.*;
import it.sevenbits.cards.web.domain.forms.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import it.sevenbits.cards.web.domain.JsonResponse;


import java.util.Map;

@Controller
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DiscountFormValidator discountFormValidator;

    @Autowired
    private SendFormValidator sendFormValidator;

    @Autowired
    private UseFormValidator useFormValidator;

    @Autowired
    private BindFormValidator bindFormValidator;

    @Autowired
    private GenerateDiscountFormValidator generateDiscountFormValidator;

    @Autowired
    private DiscountByCampaignFormValidator discountByCampaignFormValidator;

    @Autowired
    private GenerateKeyService generateKeyService;

    @Autowired
    private GenerateUinService generateUinService;

    @Autowired
    private StoreHistoryService storeHistoryService;

    @Autowired
    private DiscountHashValidator discountHashValidator;

    @Autowired
    private DiscountHashService discountHashService;

    @Autowired
    private EmailExistValidator emailExistValidator;

    private Logger LOG = Logger.getLogger(DiscountController.class);

    //Add Discount by social networks
    @Secured("ROLE_USER")
    @RequestMapping(value = "/social_add_discount/", method = RequestMethod.GET)
    public String social_add_discount(@RequestParam String uin) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        discountService.changeUserId(uin, userService.findUserIdByUserName(userName));
        return "redirect:/personal_area";
    }

    //Use Discount
    @Secured("ROLE_STORE")
    @RequestMapping(value = "/use_discount", method = RequestMethod.POST)
    public @ResponseBody JsonResponse saveDiscounts(@ModelAttribute UseForm useForm) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JsonResponse res = new JsonResponse();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        final Map<String, String> errors = useFormValidator.validate(useForm, storeName);
        if (errors.size() == 0) {
            discountService.createFeedbackDiscountAfterUse(useForm.getKey());
            discountService.delete(useForm.getKey(), storeName);
            String description = "Использована скидка с ключом " + useForm.getKey().toString();
            storeHistoryService.save(storeName, description);
            res.setStatus("SUCCESS");
            res.setResult(null);
        }else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }

    //Show All Discounts
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/discounts", method = RequestMethod.GET)
    public String showAll(final Model model) throws ServiceException {
        model.addAttribute("discounts", discountService.findAll());
        return "home/discounts";
    }

    //Save discount after add and show all discounts
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/add_discount", method = RequestMethod.POST)
    public @ResponseBody JsonResponse saveDiscounts(@ModelAttribute(value="discount") DiscountForm discountForm) throws ServiceException {
        JsonResponse res = new JsonResponse();
        final Map<String, String> errors = discountFormValidator.validate(discountForm);
        if (errors.size() == 0) {
            discountService.save(discountForm);
            res.setStatus("SUCCESS");
            res.setResult(null);
        }else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }

    //Create discount by campaign
    @Secured("ROLE_STORE")
    @RequestMapping(value="/create_discount_by_campaign", method = RequestMethod.POST)
    public @ResponseBody JsonResponse createDiscountByCampaign(@ModelAttribute DiscountByCampaignForm discountByCampaignForm, Model model) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        String storeImage = storeService.findStoreImageByStoreName(storeName);
        final Map<String, String> errors = discountByCampaignFormValidator.validate(discountByCampaignForm);
        JsonResponse res = new JsonResponse();
        if (errors.size() == 0) {
            String generatedKey = generateKeyService.random();
            String generatedUin = generateUinService.random();
            discountService.createDiscountByCampaign(discountByCampaignForm, generatedKey, generatedUin, storeName, storeImage);
            Discount discount = discountService.findDiscountByUin(generatedUin);
            final Map<String, String> exist = emailExistValidator.validate(discountByCampaignForm.getEmail());
            if (exist.size() != 0) {
                notificationService.notificateCreate(discountByCampaignForm, discount.getId());
            }
            else{
                notificationService.notificateCreateIfExist(discountByCampaignForm,discount.getId());
            }
            String description = "Скидка сгенерирована кампанией " + discountByCampaignForm.getName() + " и отправлена пользователю " + discountByCampaignForm.getEmail();
            storeHistoryService.save(storeName, description);
            res.setStatus("SUCCESS");
            res.setResult(null);
        }
        else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }

    //Add discount
    @RequestMapping(value = "/add_discount", method=RequestMethod.GET)
    public String addDiscount(Model model) throws ServiceException{
        model.addAttribute("add", new DiscountForm());
        return "home/add_discount";
    }

    //Send discount
    @Secured("ROLE_USER")
    @RequestMapping(value = "/send_discount", method = RequestMethod.POST)
    public @ResponseBody JsonResponse bindDiscount(@ModelAttribute SendForm sendForm) throws ServiceException {
        final Map<String, String> errors = sendFormValidator.validate(sendForm);
        JsonResponse res = new JsonResponse();
        if (errors.size() == 0) {
            String userId;
            try {
                userId = userService.findUserIdByUserName(sendForm.getEmail());
            } catch (Exception e) {
                userId = "";
            }
            discountService.send(userId, sendForm.getUin(), sendForm.getEmail());
            Discount discount = discountService.findDiscountByUin(sendForm.getUin());
            final Map<String, String> exist = emailExistValidator.validate(sendForm.getEmail());
            if (exist.size() != 0) {
                notificationService.notificateSend(sendForm, discount.getId());
            }
            else{
                notificationService.notificateSendIfExist(sendForm,discount.getId());
            }
            res.setStatus("SUCCESS");
            res.setResult(null);
        } else {
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }
    //Bind discount
    @Secured("ROLE_USER")
    @RequestMapping(value = "/bind_discount", method = RequestMethod.POST)
    public @ResponseBody JsonResponse bindDiscount(@ModelAttribute BindForm form) throws ServiceException {
        JsonResponse res = new JsonResponse();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        final Map<String, String> errors = bindFormValidator.validate(form, userName);

        if (errors.size() == 0) {
            discountService.changeUserId(form.getUin(), userService.findUserIdByUserName(userName));
            res.setStatus("SUCCESS");
            res.setResult(discountService.findDiscountByUin(form.getUin()));
        }else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }

    @RequestMapping(value = "/discount_info/", method = RequestMethod.GET)
    public String activatebyhash(@RequestParam String hash, Model model) throws ServiceException{
        final Map<String, String> errors = discountHashValidator.validate(hash);
        if (errors.size() ==0) {
            discountHashService.delete(hash);
            model.addAttribute("discount", discountService.findDiscountById(discountHashService.findIdByHash(hash)));
            return "home/discount_info";
        }
        else{
            return "redirect:/homepage";
        }
    }
}
