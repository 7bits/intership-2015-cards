package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.web.domain.*;
import it.sevenbits.cards.web.service.DiscountService;
import it.sevenbits.cards.web.service.ServiceException;
import it.sevenbits.cards.web.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Logger LOG = Logger.getLogger(HomeController.class);

    //Use Discount
    @RequestMapping(value = "/use_discount", method = RequestMethod.POST)
    public String use(@ModelAttribute UseForm useForm) throws ServiceException {
        discountService.delete(useForm.getUin());
        return "redirect:/store_area";
    }
    //Show All Discounts
    @RequestMapping(value = "/discounts", method = RequestMethod.GET)
    public String showAll(final Model model) throws ServiceException {
        model.addAttribute("discounts", discountService.findAll());
        return "home/discounts";
    }
    //Show all discount after add
    @RequestMapping(value = "/add_discount", method = RequestMethod.POST)
    public String showAllAfterAdd(@ModelAttribute DiscountForm discountForm) throws ServiceException {
        LOG.debug(discountForm);
        discountService.save(discountForm);
        return "redirect:/add_discount";
    }
    //Add discount
    @RequestMapping(value = "/add_discount", method=RequestMethod.GET)
    public String addDiscount(Model model) throws ServiceException{
        model.addAttribute("add", new DiscountForm());
        return "home/add_discount";
    }
    //Send discount
    @RequestMapping(value = "/send_discount", method = RequestMethod.POST)
    public String send(@ModelAttribute SendForm form) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        discountService.send(form.getUserId(), form.getUin(), userName);
        return "redirect:/personal_area";
    }
    //Bind discount
    @RequestMapping(value = "/bind_discount", method = RequestMethod.POST)
    public String tradePost(@ModelAttribute UseForm form) throws RepositoryException, ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        discountService.changeUserId(form.getUin(), userService.findUserIdByUserName(userName));
        return "redirect:/personal_area";
    }
}
