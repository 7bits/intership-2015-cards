package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.web.domain.forms.SaveStoreForm;
import it.sevenbits.cards.web.domain.forms.AddStoreForm;
import it.sevenbits.cards.service.ServiceException;
import it.sevenbits.cards.service.StoreService;
import it.sevenbits.cards.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Secured("ROLE_ADMIN")
@Controller
public class AdminController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserService userService;
    private Logger LOG = Logger.getLogger(AdminController.class);

    @RequestMapping(value = "/admin_area", method = RequestMethod.GET)
    public String adminAreaGet(){
        return "home/admin_area";
    }

    //Store Area Post Method
    @RequestMapping(value = "/admin_area", method = RequestMethod.POST)
    public String adminAreaPost() {
        return "home/admin_area";
    }

    //Add Store
    @RequestMapping(value = "/add_store", method = RequestMethod.POST)
    public String addStoreToDataBaseAndUpdateUserRole(@ModelAttribute AddStoreForm addStoreForm) throws ServiceException {
        storeService.save(addStoreForm);
        userService.changeUserRoleByEmail("ROLE_STORE", addStoreForm.getEmail());
        return "redirect:/add_store";
    }

    //Add Store
    @RequestMapping(value = "/add_store", method=RequestMethod.GET)
    public String addStore(Model model){
        model.addAttribute("add", new AddStoreForm());
        return "home/add_store";
    }
}