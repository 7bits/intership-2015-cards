package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.web.domain.RegistrationForm;
import it.sevenbits.cards.web.service.ServiceException;
import it.sevenbits.cards.web.service.UsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;
    private Logger LOG = Logger.getLogger(HomeController.class);

    @RequestMapping(value = "/user_registration", method = RequestMethod.POST)
    public String user_registration(@ModelAttribute RegistrationForm registrationForm) throws ServiceException {
        usersService.saveUser(registrationForm);
        return "redirect:/homepage";
    }
}
