package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.web.domain.*;
import it.sevenbits.cards.web.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import it.sevenbits.cards.web.domain.JsonResponse;


import java.util.Map;

@Controller
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private UserService userService;

    @Autowired
    private GenerateUniqueCode generateUniqueCode;

    @Autowired
    private StoreService storeService;

    @Autowired
    private DiscountFormValidator discountFormValidator;

    @Autowired
    private SendFormValidator sendFormValidator;

    @Autowired
    private UseFormValidator useFormValidator;

    @Autowired
    private BindFormValidator bindFormValidator;

    private Logger LOG = Logger.getLogger(HomeController.class);

    @Secured("ROLE_USER")
    @RequestMapping(value = "/social_add_discount/", method = RequestMethod.GET)
    public String social_add_discount(@RequestParam String uin) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        discountService.changeUserId(uin, userService.findUserIdByUserName(userName));
        return "redirect:/personal_area";
    }
    //Use Discount
    @Secured("ROLE_STORE")
    @RequestMapping(value = "/use_discount", method = RequestMethod.POST)
    public String use(@ModelAttribute UseForm useForm, Model model) throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String storeName = storeService.findStoreNameByUserId(userService.findUserIdByUserName(authentication.getName()));
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
    public @ResponseBody JsonResponse saveDiscounts(@ModelAttribute(value="discount") DiscountForm discountForm) throws ServiceException {
        JsonResponse res = new JsonResponse();
        final Map<String, String> errors = discountFormValidator.validate(discountForm);

        if (errors.size() == 0) {
            discountService.save(discountForm);
            res.setStatus("SUCCESS");
            res.setResult(null);
        }else{
            res.setStatus("FAIL");
            res.setResult(errors);
        }
        return res;
    }
    //Generate discount
    @Secured("ROLE_STORE")
    @RequestMapping(value="/generate_discount", method = RequestMethod.POST)
    public String generateDiscount(@ModelAttribute GenerateDiscountForm generateDiscountForm) throws ServiceException, RepositoryException{
        String generateKey=generateUniqueCode.random();
        String generateUin=generateUniqueCode.random();
        discountService.generateDiscount(generateDiscountForm, generateKey, generateUin);
        return "redirect:/store_area";
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
    public String bindDiscount(@ModelAttribute BindForm bindForm, Model model) throws ServiceException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        final Map<String, String> errors = bindFormValidator.validate(bindForm, userName);
        if (errors.size() != 0) {
            model.addAttribute("errors", errors);
            return "redirect:/personal_area";
        }
        discountService.changeUserId(bindForm.getUin(), userService.findUserIdByUserName(userName));
        return "redirect:/personal_area";
    }
}
