package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.domain.StoreHistory;
import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.web.domain.*;
import it.sevenbits.cards.web.service.*;
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
    private NewDiscountNotificationService notificationService;

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
    private GenerateKey generateKey;

    @Autowired
    private GenerateUin generateUin;

    @Autowired
    private StoreHistoryService storeHistoryService;

    private Logger LOG = Logger.getLogger(HomeController.class);

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
    public @ResponseBody JsonResponse saveDiscounts(@ModelAttribute UseForm form) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JsonResponse res = new JsonResponse();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        final Map<String, String> errors = useFormValidator.validate(form, storeName);
        if (errors.size() == 0) {
            discountService.delete(form.getKey(), storeName);
            res.setStatus("SUCCESS");
            res.setResult(null);
            StoreHistory storeHistory = new StoreHistory();
            storeHistory.setStoreName(storeName);
            storeHistory.setDescription("Использована скидка с ключом " + form.getKey().toString());
            storeHistoryService.save(storeHistory);
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
    public @ResponseBody JsonResponse createDiscountByCampaign(@ModelAttribute DiscountByCampaignForm discountByCampaignForm, Model model) throws ServiceException, RepositoryException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        String storeImage = storeService.findStoreImageByStoreName(storeName);
        final Map<String, String> errors = discountByCampaignFormValidator.validate(discountByCampaignForm);
        JsonResponse res = new JsonResponse();
        if (errors.size() == 0) {
            String generatedKey= generateKey.random();
            String generatedUin= generateUin.random();
            discountService.createDiscountByCampaign(discountByCampaignForm, generatedKey, generatedUin, storeName, storeImage);
            notificationService.notificateCreate(discountByCampaignForm);
            res.setStatus("SUCCESS");
            res.setResult(null);
            StoreHistory storeHistory = new StoreHistory();
            storeHistory.setStoreName(storeName);
            storeHistory.setDescription("Скидка сгенерирована кампанией " + discountByCampaignForm.getName() + " и отправлена пользователю " + discountByCampaignForm.getEmail());
            storeHistoryService.save(storeHistory);
        }
        else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }
    //Generate discount
    @Secured("ROLE_STORE")
    @RequestMapping(value="/generate_discount", method = RequestMethod.POST)
    public String generateDiscount(@ModelAttribute GenerateDiscountForm generateDiscountForm, Model model) throws ServiceException, RepositoryException{
        final Map<String, String> errors = generateDiscountFormValidator.validate(generateDiscountForm);
        if (errors.size() != 0) {
            model.addAttribute("errors", errors);
            return "redirect:/store_area";
        }
        String generatedKey= generateKey.random();
        String generatedUin= generateUin.random();
        discountService.generateDiscount(generateDiscountForm, generatedKey, generatedUin);
        return "redirect:/store_area";
    }
    //Add discount
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/add_discount", method=RequestMethod.GET)
    public String addDiscount(Model model) throws ServiceException{
        model.addAttribute("add", new DiscountForm());
        return "home/add_discount";
    }
    //Send discount
    @Secured("ROLE_USER")
    @RequestMapping(value = "/send_discount", method = RequestMethod.POST)
    public @ResponseBody JsonResponse bindDiscount(@ModelAttribute SendForm form) throws ServiceException {
        final Map<String, String> errors = sendFormValidator.validate(form);
        JsonResponse res = new JsonResponse();
        if (errors.size() == 0) {
            discountService.send(userService.findUserIdByUserName(form.getEmail()), form.getUin());
            notificationService.notificateSend(form);
            res.setStatus("SUCCESS");
            res.setResult(null);
        }else{
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
}
