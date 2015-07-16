package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.web.service.UsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;
    private Logger LOG = Logger.getLogger(HomeController.class);
}
