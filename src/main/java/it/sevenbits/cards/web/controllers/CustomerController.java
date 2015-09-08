package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.service.DiscountService;
import it.sevenbits.cards.service.ServiceException;
import it.sevenbits.cards.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Secured("ROLE_USER")
@Controller
public class CustomerController {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscountService discountService;

    private Logger LOG = Logger.getLogger(CustomerController.class);

    //Personal Area Get Method
    @RequestMapping(value = "/personal_area", method = RequestMethod.GET)
    public String personalArea(final Model model) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        model.addAttribute("userName", userName);
        String userId = userService.findUserIdByUserName(userName);
        model.addAttribute("userId", userId);
        model.addAttribute("discountsForUse", discountService.findAllForUse(userId));
        model.addAttribute("discountsForSend", discountService.findAllForSend(userId));
        return "home/personal_area";
    }

    //Personal Area Post Method
    @RequestMapping(value = "/personal_area", method = RequestMethod.POST)
    public String personalAreaPost(){
        return "redirect:/personal_area";
    }
}
