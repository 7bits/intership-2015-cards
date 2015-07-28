package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.validation.EmailValidation;
import it.sevenbits.cards.web.domain.*;
import it.sevenbits.cards.web.service.DiscountService;
import it.sevenbits.cards.web.service.ServiceException;
import it.sevenbits.cards.web.service.StoreService;
import it.sevenbits.cards.web.service.UserService;
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

@Controller
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    private Logger LOG = Logger.getLogger(HomeController.class);

    //Use Discount
    @Secured("ROLE_STORE")
    @RequestMapping(value = "/use_discount", method = RequestMethod.POST)
    public String use(@ModelAttribute UseForm useForm) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        discountService.delete(useForm.getUin(), storeName);
        return "redirect:/store_area";
    }
    //Show All Discounts
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/discounts", method = RequestMethod.GET)
    public String showAll(final Model model) throws ServiceException {
        model.addAttribute("discounts", discountService.findAll());
        return "home/discounts";
    }
    //Show all discount after add
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/add_discount", method = RequestMethod.POST)
    public String showAllAfterAdd(@ModelAttribute DiscountForm discountForm) throws ServiceException {
        LOG.debug(discountForm);
        discountService.save(discountForm);
        return "redirect:/add_discount";
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
    public String send(@ModelAttribute SendForm form) throws ServiceException{
        if( EmailValidation.checkEmailValidity(form.getEmail())) {
            discountService.send(userService.findUserIdByUserName(form.getEmail()), form.getUin());
        }
        else {
            discountService.send(form.getEmail(), form.getUin());
        }
        return "redirect:/personal_area";
    }
    //Bind discount
    @Secured("ROLE_USER")
    @RequestMapping(value = "/bind_discount", method = RequestMethod.POST)
    public String tradePost(@ModelAttribute UseForm form) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        discountService.changeUserId(form.getUin(), userService.findUserIdByUserName(userName));
        return "redirect:/personal_area";
    }
}
