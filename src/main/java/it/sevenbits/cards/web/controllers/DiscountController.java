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

    @Autowired
    private DiscountService discountService;

    private Logger LOG = Logger.getLogger(DiscountController.class);

    //Add Discount by social networks
    @Secured("ROLE_USER")
    @RequestMapping(value = "/social_add_discount/", method = RequestMethod.GET)
    public String socialAddDiscount(@RequestParam String hash) throws ServiceException{
        discountService.changeDiscountOwner(hash);
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

    //Send discount
    @Secured("ROLE_USER")
    @RequestMapping(value = "/send_discount", method = RequestMethod.POST)
    public @ResponseBody JsonResponse sendDiscount(@ModelAttribute SendForm sendForm) throws ServiceException {
        return jsonHandler.sendDiscount(sendForm);
    }

    @RequestMapping(value = "/discount_info/", method = RequestMethod.GET)
    public String activatebyhash(@RequestParam String hash, Model model) throws ServiceException{
        model.addAllAttributes(modelBuilder.buildDiscountInfo(hash));
        if(model == null) {
            return "redirect:/homepage";
        }
        else {
            return "home/discount_info";
        }
    }
}
