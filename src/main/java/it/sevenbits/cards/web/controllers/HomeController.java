package it.sevenbits.cards.web.controllers;
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
    //Index page
    @RequestMapping(value = "/store_area", method = RequestMethod.GET)
    public String storeArea(final Model model) {
        return "home/store_area";
    }
    //Authorization page
    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public String authorization_post(final Model model) {
        model.addAttribute("authorization", new AuthorizationForm());
        return "home/authorization";
    }
}