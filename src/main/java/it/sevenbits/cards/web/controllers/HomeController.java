package it.sevenbits.cards.web.controllers;
import it.sevenbits.cards.core.domain.AccountActivation;
import it.sevenbits.cards.core.domain.Role;
import it.sevenbits.cards.core.domain.Store;
import it.sevenbits.cards.core.domain.StoreHistory;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.web.domain.*;
import it.sevenbits.cards.web.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountActivationService activationService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private StoreHistoryService storeHistoryService;

    @Autowired
    private PasswordRestoreService restoreService;

    @Autowired
    private RegistrationFormValidator registrationFormValidator;

    @Autowired
    private SettingsFormValidator settingsFormValidator;

    @Autowired
    private PasswordRestoreFormValidator passwordRestoreFormValidator;

    @Autowired
    private NewPasswordFormValidator newPasswordFormValidator;

    @Autowired
    private HashValidator hashValidator;

    @Autowired
    private AccountActivateHashValidator accountActivateHashValidator;

    @Autowired
    private StoreService storeService;

    @Autowired
    private IdFormValidator idFormValidator;

    @Autowired
    private FeedbackFormValidator feedbackFormValidator;

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

    //Homepage Error
    @RequestMapping(value = "/login/", method = RequestMethod.GET)
    public String loginError(HttpServletRequest httpServletRequest, @RequestParam String loginError, Model model, HttpServletRequest request) {
        model.addAttribute("loginError", loginError);
        return "home/homepage";
    }

    //About
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {return "home/about";}

    //Developers
    @RequestMapping(value = "/developers", method = RequestMethod.GET)
    public String developers() {return "home/developers";}

    //Registration
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {return "home/registration";
    }

    //Registration
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public @ResponseBody JsonResponse registration(@ModelAttribute RegistrationForm form) throws ServiceException {
        final Map<String, String> errors = registrationFormValidator.validate(form);
        JsonResponse res = new JsonResponse();
        if (errors.size() != 0) {
            res.setStatus("FAIL");
            res.setResult(errors);
        } else {
            userService.createUser(form);
            AccountActivation activation = activationService.generateActivationHash(form);
            activationService.sendEmail(activation);
            res.setStatus("SUCCESS");
            res.setResult(null);
        }
        return res;
    }

    @RequestMapping(value = "/registration/", method = RequestMethod.GET)
    public String activatebyhash(@RequestParam(required = false) String hash, Model model) {
        final Map<String, String> errors = accountActivateHashValidator.validate(hash);
        if (errors.size() ==0) {
            activationService.activateByHash(hash);
            model.addAttribute("accActivate", "Регистрация успешно завершена");
        }else{
            return "redirect:/registration";
        }
        return "home/registration";

    }

    //Password Restore
    @RequestMapping(value = "/password_restore", method = RequestMethod.GET)
    public String restorePassword() {return "home/password_restore";}

    //Password Restore
    @RequestMapping(value = "/password_restore/", method = RequestMethod.GET)
    public String restorePasswordHash(@RequestParam String hash, Model model) {
        final Map<String, String> errors = hashValidator.validate(hash);
        if (errors.size()==0) {
            if (hash.substring(0, 6).equals("delete")) {
                restoreService.deleteByHash(hash);
                return "home/homepage";
            }
            model.addAttribute("hash", hash);
            return "home/new_password";
        } else {
            return "redirect:/password_restore";
        }
    }
    //Password Restore
    @RequestMapping(value = "/password_restore/", method = RequestMethod.POST)
    public @ResponseBody JsonResponse newPassword(@ModelAttribute NewPasswordForm newPasswordForm) throws ServiceException{
        JsonResponse res = new JsonResponse();
        final Map<String, String> errors = newPasswordFormValidator.validate(newPasswordForm);
        if (errors.size() == 0) {
            restoreService.restorePassword(newPasswordForm);
            res.setStatus("SUCCESS");
            res.setResult(null);
        }else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }

    //Password Restore
    @RequestMapping(value = "/password_restore", method = RequestMethod.POST)
    public @ResponseBody JsonResponse restorePassword(@ModelAttribute PasswordRestoreForm passwordRestoreForm) throws ServiceException {
        JsonResponse res = new JsonResponse();
        final Map<String, String> errors = passwordRestoreFormValidator.validate(passwordRestoreForm);
        if (errors.size() == 0) {
            res.setStatus("SUCCESS");
            res.setResult(null);
            restoreService.sendEmail(restoreService.generateHash(passwordRestoreForm));
        }else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }

    //Feedback
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

    //Feedback send
    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public @ResponseBody JsonResponse feedback(@ModelAttribute FeedbackForm feedbackForm) throws ServiceException {
        JsonResponse res = new JsonResponse();
        final Map<String, String> errors = feedbackFormValidator.validate(feedbackForm);
        if (errors.size() == 0) {
            res.setStatus("SUCCESS");
            res.setResult(null);
            userService.sendMailToFeedback(feedbackForm);
        }else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
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
    public String storePageNew(final Model model) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        model.addAttribute("activeCampaigns", campaignService.findAllActive(storeName));
        model.addAttribute("notActiveCampaigns", campaignService.findAllNotActive(storeName));
        model.addAttribute("history", storeHistoryService.findAll(storeName));
        return "home/store_area";
    }

    //Store Area Post Method
    @Secured("ROLE_STORE")
    @RequestMapping(value = "/store_area", method = RequestMethod.POST)
    public String storeAreaPost(final Model model) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        model.addAttribute("activeCampaigns", campaignService.findAllActive(storeName));
        model.addAttribute("notActiveCampaigns", campaignService.findAllNotActive(storeName));
        model.addAttribute("history", storeHistoryService.findAll(storeName));
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

    //Store Area Post Method
    @Secured("ROLE_STORE")
    @RequestMapping(value = "/store_history", method = RequestMethod.GET)
    public String storeHistory(final Model model) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        model.addAttribute("history", storeHistoryService.findAll(storeName));
        return "home/store_history";
    }

    @Secured("ROLE_STORE")
    @RequestMapping(value = "/change_campaign_status", method = RequestMethod.POST)
    public String changeCampaignEnableStatus(@ModelAttribute IdForm idForm) throws ServiceException{
        final Map<String, String> errors = idFormValidator.validate(idForm.getId());
        if(errors.size() == 0){
            campaignService.changeCampaignEnableStatus(idForm.getId());
        }
        return "redirect:/store_area";
    }
}