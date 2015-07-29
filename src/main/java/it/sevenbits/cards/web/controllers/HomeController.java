package it.sevenbits.cards.web.controllers;
import it.sevenbits.cards.core.domain.Role;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.web.domain.FeedbackForm;
import it.sevenbits.cards.web.domain.NewPasswordForm;
import it.sevenbits.cards.web.domain.PasswordRestoreForm;
import it.sevenbits.cards.web.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.net.Authenticator;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;

import it.sevenbits.cards.web.domain.RegistrationForm;

@Controller
public class HomeController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordRestoreService restoreService;

    @Autowired
    private RegistrationFormValidator registrationFormValidator;

    private Logger LOG = Logger.getLogger(HomeController.class);

    //Success
    @RequestMapping(value="/success",method = RequestMethod.GET)
    public String success(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOG.info(user.getRole());
        if(user.getRole().equals(Role.ROLE_ADMIN)) {
            return "redirect:/admin_area";
        }
        if(user.getRole().equals(Role.ROLE_STORE)) {
            return "redirect:/store_area";
        }
        if(user.getRole().equals(Role.ROLE_USER)) {
            return "redirect:/personal_area";
        }
        return "redirect:/403";
    }

    @RequestMapping(value="/403",method = RequestMethod.GET)
    public String accessDeny(){
        return "/403";
    }

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
    public String registration(@ModelAttribute RegistrationForm registrationForm, Model model) throws ServiceException, RepositoryException {
        final Map<String, String> errors = registrationFormValidator.validate(registrationForm);
        if (errors.size() != 0) {
            model.addAttribute("errors", errors);
            return "home/registration";
        }
        userService.createUser(registrationForm);
        return "home/registration";
    }

    //Password restore
    @RequestMapping(value = "/password_restore", method = RequestMethod.GET)
    public String restorePassword() {return "home/password_restore";}

    @RequestMapping(value = "/password_restore/", method = RequestMethod.GET)
    public String restorePasswordHash(@RequestParam String hash, Model model) {
        if (hash == null || hash.length() == 0) {
            return "home/password_restore";
        } else {
            restoreService.printHash(hash);
            model.addAttribute("hash", hash);
            return "home/new_password";
        }
    }

    @RequestMapping(value = "/password_restore/", method = RequestMethod.POST)
    public String newPassword(@ModelAttribute NewPasswordForm form) throws ServiceException{
        restoreService.restorePassword(form);
        return "redirect:/password_restore";
    }

    @RequestMapping(value = "/password_restore", method = RequestMethod.POST)
    public String restorePassword(@ModelAttribute PasswordRestoreForm form) throws ServiceException {
        restoreService.sendEmail(restoreService.generateHash(form));
        return "redirect:/password_restore";
    }

    //feedback
    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String feedback(final Model model) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        if (userName == "anonymousUser") {
            userName = "";
        }
        model.addAttribute("userName", userName);
        return "home/feedback";
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public String feedback(@ModelAttribute FeedbackForm form) throws ServiceException {
        userService.sendMailToFeedback(form);
        return "redirect:/feedback";
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
    public String personalAreaPost() throws ServiceException {
        return "redirect:/personal_area";
    }

    //Store Area Get Method

    @Secured("ROLE_STORE")
    @RequestMapping(value = "/store_area", method = RequestMethod.GET)
    public String storeAreaGet(){
        return "home/store_area";
    }

    //Store Area Post Method
    @Secured("ROLE_STORE")
    @RequestMapping(value = "/store_area", method = RequestMethod.POST)
    public String storeAreaPost() {
        return "home/store_area";
    }

    //Store Area Get Method

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin_area", method = RequestMethod.GET)
    public String adminAreaGet(){
        return "home/admin_area";
    }

    //Store Area Post Method
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin_area", method = RequestMethod.POST)
    public String adminAreaPost() {
        return "home/admin_area";
    }
}