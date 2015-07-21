package it.sevenbits.cards.web.controllers;
import it.sevenbits.cards.web.service.DiscountService;
import it.sevenbits.cards.web.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import java.net.Authenticator;

@Controller
public class HomeController {
    @Autowired
    private DiscountService discountService;
    private Logger LOG = Logger.getLogger(HomeController.class);

    //Login
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(HttpServletRequest request) {
        return "redirect:/homepage";
    }

    //Homepage
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "home/homepage";
    }

    //Homepage
    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String homepage(final Model model) {
        return "home/homepage";
    }

    //Personal Area Get Method
    @RequestMapping(value = "/personal_area", method = RequestMethod.GET)
    public String personalArea(final Model model) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        model.addAttribute("userName", userName);
        model.addAttribute("discountsForUse", discountService.findAllForUse(userName));
        model.addAttribute("discountsForSend", discountService.findAllForSend(userName));
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