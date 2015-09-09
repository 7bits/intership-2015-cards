package it.sevenbits.cards.web.controllers;
import it.sevenbits.cards.core.domain.Role;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.service.*;
import it.sevenbits.cards.service.validators.*;
import it.sevenbits.cards.web.domain.forms.*;
import it.sevenbits.cards.web.domain.JsonResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService activationService;

    @Autowired
    private PasswordRestoreService restoreService;

    @Autowired
    private RegistrationFormValidator registrationFormValidator;

    @Autowired
    private PasswordRestoreFormValidator passwordRestoreFormValidator;

    @Autowired
    private NewPasswordFormValidator newPasswordFormValidator;

    @Autowired
    private HashValidator hashValidator;

    @Autowired
    private AccountActivateHashValidator accountActivateHashValidator;

    @Autowired
    private FeedbackFormValidator feedbackFormValidator;

    private Logger LOG = Logger.getLogger(HomeController.class);

    //Success
    @RequestMapping(value="/success",method = RequestMethod.GET)
    public String success(){
        //NEED TO FIX THIS
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
    public String registration(Model model) {
        model.addAttribute("enterEmail", "Введите адрес эл. почты");
        model.addAttribute("enterPassword", "Введите пароль");
        model.addAttribute("registration", "Регистрация");
        model.addAttribute("signUp", "Зарегистрироваться");
        return "home/registration";
    }

    //Registration
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse registration(@ModelAttribute RegistrationForm registrationForm) throws ServiceException {
        final Map<String, String> errors = registrationFormValidator.validate(registrationForm);
        JsonResponse res = new JsonResponse();
        if (errors.size() != 0) {
            res.setStatus("FAIL");
            res.setResult(errors);
        } else {
            userService.createUser(registrationForm);
            activationService.sendEmail(activationService.generateActivationHash(registrationForm));
            res.setStatus("SUCCESS");
            res.setResult(null);
        }
        return res;
    }

    @RequestMapping(value = "/registration/", method = RequestMethod.GET)
    public String activatebyhash(@RequestParam(required = false) String hash, Model model) throws ServiceException{
        final Map<String, String> errors = accountActivateHashValidator.validate(hash);
        if (errors.size() ==0) {
            String email = activationService.findEmailByHash(hash);
            activationService.activateByHash(hash);
            discountService.addExistDiscountsByEmail(email, userService.findUserIdByUserName(email));
            model.addAttribute("accActivate", "Регистрация успешно завершена");
        }else {
            return "redirect:/registration";
        }
        return "home/registration";
    }

    //Password Restore
    @RequestMapping(value = "/password_restore", method = RequestMethod.GET)
    public String restorePassword() {return "home/password_restore";}

    //Password Restore
    @RequestMapping(value = "/password_restore/", method = RequestMethod.GET)
    public String restorePasswordHash(@RequestParam String hash, Model model) throws ServiceException{
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
            restoreService.sendEmail(restoreService.generateHash(passwordRestoreForm));
            res.setStatus("SUCCESS");
            res.setResult(null);
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
        //Can't remove logic from this function cuz we don't use services for it
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
            userService.sendMailToFeedback(feedbackForm);
            res.setStatus("SUCCESS");
            res.setResult(null);
        }else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }
}