package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.validation.EmailValidation;
import it.sevenbits.cards.web.domain.*;
import it.sevenbits.cards.web.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private DiscountFormValidator discountFormValidator;

    @Autowired
    private SendFormValidator sendFormValidator;

    @Autowired
    private UseFormValidator useFormValidator;

    private Logger LOG = Logger.getLogger(HomeController.class);

    //Use Discount
    @Secured("ROLE_STORE")
    @RequestMapping(value = "/use_discount", method = RequestMethod.POST)
    public String use(@ModelAttribute UseForm useForm, Model model) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
        LOG.info(useForm.getKey());
        LOG.info(storeName);
        final Map<String, String> errors = useFormValidator.validate(useForm, storeName);
        if (errors.size() != 0) {
            model.addAttribute("errors", errors);
            return "redirect:/store_area";
        }
        discountService.delete(useForm.getKey(), storeName);
        return "redirect:/store_area";
    }
    //Show All Discounts
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/discounts", method = RequestMethod.GET)
    public String showAll(final Model model) throws ServiceException {
        model.addAttribute("discounts", discountService.findAll());
        return "home/discounts";
    }
    //Save discount after add and show all discounts
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/add_discount", method = RequestMethod.POST)
    public String saveDiscountAndShowAllAfterAdd(@ModelAttribute DiscountForm discountForm, Model model) throws ServiceException {
        LOG.debug(discountForm);
        final Map<String, String> errors = discountFormValidator.validate(discountForm);
        if (errors.size() != 0) {
            model.addAttribute("errors", errors);
            return "home/add_discount";
        }
        discountService.save(discountForm);
        return "home/add_discount";
    }
    //Add discount
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/add_discount", method=RequestMethod.GET)
    public String addDiscount(Model model) throws ServiceException{
        model.addAttribute("add", new DiscountForm());
        return "home/add_discount";
    }
    //Send discount
    @Secured("ROLE_USER")
    @RequestMapping(value = "/send_discount", method = RequestMethod.POST)
    public String sendDiscount(@ModelAttribute SendForm sendForm, Model model ) throws ServiceException, RepositoryException{
        final Map<String, String> errors = sendFormValidator.validate(sendForm);
        if (errors.size() != 0) {
            model.addAttribute("errors", errors);
            return "redirect:/personal_area";
        }
        discountService.send(userService.findUserIdByUserName(sendForm.getEmail()), sendForm.getUin());
        return "redirect:/personal_area";
    }
    //Bind discount
    @Secured("ROLE_USER")
    @RequestMapping(value = "/bind_discount", method = RequestMethod.POST)
    public String bindDiscount(@ModelAttribute UseForm form) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        discountService.changeUserId(form.getKey(), userService.findUserIdByUserName(userName));
        return "redirect:/personal_area";
    }
}
