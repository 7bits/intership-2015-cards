package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.core.domain.Role;
import it.sevenbits.cards.web.domain.SaveStoreForm;
import it.sevenbits.cards.web.domain.AddStoreForm;
import it.sevenbits.cards.web.service.ServiceException;
import it.sevenbits.cards.web.service.StoreService;
import it.sevenbits.cards.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;
    private Logger LOG = Logger.getLogger(UserController.class);

    //Show all discount after add
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/add_store", method = RequestMethod.POST)
    public String addStoreToDataBaseAndUpdateUserRole(@ModelAttribute AddStoreForm addStoreForm) throws ServiceException {
        LOG.debug(addStoreForm);
        String storeId = userService.findUserIdByUserName(addStoreForm.getEmail());
        SaveStoreForm saveStoreForm = new SaveStoreForm();
        saveStoreForm.setUserId(storeId);
        saveStoreForm.setStoreName(addStoreForm.getStoreName());
        saveStoreForm.setStoreImage(addStoreForm.getStoreImage());
        storeService.save(saveStoreForm);
        userService.changeUserRoleByUserId("ROLE_STORE", storeId);
        return "redirect:/add_store";
    }
    //Add discount
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/add_store", method=RequestMethod.GET)
    public String addStore(Model model) throws ServiceException {
        model.addAttribute("add", new AddStoreForm());
        return "home/add_store";
    }
}