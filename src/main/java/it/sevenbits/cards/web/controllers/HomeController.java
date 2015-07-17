package it.sevenbits.cards.web.controllers;
import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.web.domain.*;
import it.sevenbits.cards.web.service.DiscountsService;
import it.sevenbits.cards.web.service.ServiceException;
import it.sevenbits.cards.web.service.UsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @Autowired
    private DiscountsService discountsService;
    private Logger LOG = Logger.getLogger(HomeController.class);
    //Index page
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(final Model model) {
        return "home/homepage";
    }
    //Index page
    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String homepage(final Model model) {
        return "home/homepage";
    }
    //Index page
    @RequestMapping(value = "/personal_area", method = RequestMethod.GET)
    public String personalArea(final Model model) throws ServiceException {
        model.addAttribute("discounts", discountsService.findAll());
        return "home/personal_area";
    }
    @RequestMapping(value = "/personal_area", method = RequestMethod.POST)
    public String personalAreaPost(final Model model) throws ServiceException {

        return "redirect:/personal_area";
    }
    //Index page
    @RequestMapping(value = "/store_area", method = RequestMethod.GET)
    public String storeArea(final Model model) {
        return "home/store_area";
    }
    //Authorization page
    //Trade discount POST
    @RequestMapping(value = "/bind_discount", method = RequestMethod.POST)
    public String tradePost(@ModelAttribute UseForm form, final Model model) throws ServiceException{
        Discount discount = new Discount();
        discount.setUin(form.getUin());
        DiscountForm discountForm = new DiscountForm();
        discountForm.setUin(form.getUin());
        discountsService.changeUserId(discountForm);
        model.addAttribute("discounts", discountsService.findUserId(discount));
        return "redirect:/personal_area";
    }

    @RequestMapping(value = "/send_discount", method = RequestMethod.POST)
    public String send(@ModelAttribute SendForm form, final Model model) throws ServiceException{
        DiscountForm discountForm = new DiscountForm();
        discountForm.setUin(form.getUin());
        discountForm.setUserId(form.getUserId());
        discountsService.sendDiscount(discountForm);
        return "redirect:/personal_area";
    }
}