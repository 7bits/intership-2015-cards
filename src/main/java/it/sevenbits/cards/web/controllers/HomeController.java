package it.sevenbits.cards.web.controllers;
import it.sevenbits.cards.core.repository.RepositoryException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import java.net.Authenticator;
import it.sevenbits.cards.web.domain.RegistrationForm;
import it.sevenbits.cards.web.service.DiscountService;
import it.sevenbits.cards.web.service.ServiceException;
import it.sevenbits.cards.web.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private UserService userService;
    private Logger LOG = Logger.getLogger(HomeController.class);

    //Login
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(HttpServletRequest request) {
        return "redirect:/homepage";
    }

    //Homepage
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:/homepage";
    }

    //Homepage
    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String homepage() {
        return "home/homepage";
    }

    //Registration
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {return "home/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String user_registration(@ModelAttribute RegistrationForm form) throws ServiceException, RepositoryException {
        userService.createUser(form);
        return "redirect:/registration";
    }

    //Personal Area Get Method
    @Secured("ROLE_USER")
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
    @Secured("ROLE_USER")
    @RequestMapping(value = "/personal_area", method = RequestMethod.POST)
    public String personalAreaPost(final Model model) throws ServiceException {
        return "redirect:/personal_area";
    }

    //Store Area Get Method

    @Secured("ROLE_STORE")
    @RequestMapping(value = "/store_area", method = RequestMethod.GET)
    public String storeAreaGet(final Model model) {
        return "home/store_area";
    }

    //Store Area Post Method
    @Secured("ROLE_STORE")
    @RequestMapping(value = "/store_area", method = RequestMethod.POST)
    public String storeAreaPost(final Model model) {
        return "home/store_area";
    }
}