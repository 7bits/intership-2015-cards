package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.web.domain.*;
import it.sevenbits.cards.web.service.DiscountService;
import it.sevenbits.cards.web.service.ServiceException;
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

    private Logger LOG = Logger.getLogger(HomeController.class);

    //Use Discount
    @RequestMapping(value = "/use_discount", method = RequestMethod.POST)
    public String use(@ModelAttribute UseForm useForm, final Model model) throws ServiceException {
        DiscountForm discountForm = new DiscountForm();
        discountForm.setUin(useForm.getUin());
        model.addAttribute("use", useForm);
        discountService.delete(discountForm);
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
        return "redirect:/discounts";
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
    public String tradePost(@ModelAttribute UseForm form, final Model model) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Discount discount = new Discount();
        discount.setUin(form.getUin());
        DiscountForm discountForm = new DiscountForm();
        discountForm.setUin(form.getUin());
        discountService.changeUserId(discountForm);
        return "redirect:/personal_area";
    }
}
