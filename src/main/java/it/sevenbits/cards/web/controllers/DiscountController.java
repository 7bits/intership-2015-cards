package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.service.*;
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
    private JsonHandler jsonHandler;

    @Autowired
    private ModelBuilder modelBuilder;

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
    public @ResponseBody JsonResponse saveDiscounts(@ModelAttribute KeyForm keyForm) throws ServiceException {
        return jsonHandler.useDiscount(keyForm);
    }

    //Create discount by campaign
    @Secured("ROLE_STORE")
    @RequestMapping(value="/create_discount_by_campaign", method = RequestMethod.POST)
    public @ResponseBody JsonResponse createDiscountByCampaign(@ModelAttribute DiscountByCampaignForm discountByCampaignForm) throws ServiceException{
        return jsonHandler.createDiscountByCampaign(discountByCampaignForm);
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
