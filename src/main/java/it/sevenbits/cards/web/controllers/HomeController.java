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
    //Homepage from root
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(final Model model) {
        return "home/homepage";
    }
    //Homepage
    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String homepage(final Model model) {
        return "home/homepage";
    }
    //Registration
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {return "home/registration";}
    //Personal Area Get Method
    @RequestMapping(value = "/personal_area", method = RequestMethod.GET)
    public String personalArea(final Model model) throws ServiceException {
        model.addAttribute("discounts", discountsService.findAllDiscounts());
        return "home/personal_area";
    }
    //Personal Area Post Method
    @RequestMapping(value = "/personal_area", method = RequestMethod.POST)
    public String personalAreaPost(final Model model) throws ServiceException {

        return "redirect:/personal_area";
    }
    //Store Area Get Method
    @RequestMapping(value = "/store_area", method = RequestMethod.GET)
    public String storeAreaGet(final Model model) {
        return "home/store_area";
    }
    //Store Area Post Method
    @RequestMapping(value = "/store_area", method = RequestMethod.POST)
    public String storeAreaPost(final Model model) {
        return "home/store_area";
    }
}