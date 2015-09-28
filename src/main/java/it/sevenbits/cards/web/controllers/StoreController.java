package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.service.*;
import it.sevenbits.cards.web.domain.forms.IdForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Secured("ROLE_STORE")
@Controller
public class StoreController {

    @Autowired
    private ModelBuilder modelBuilder;

    @Autowired
    private JsonHandler jsonHandler;

    //Store Area Get Method
    @RequestMapping(value = "/store_area", method = RequestMethod.GET)
    public String storeAreaGet(final Model model) throws ServiceException {
        model.addAllAttributes(modelBuilder.buildStore());
        return "home/store_area";
    }

    //Store Area Post Method
    @RequestMapping(value = "/store_area", method = RequestMethod.POST)
    public String storeAreaPost() throws ServiceException {
        return "redirect:/store_area";
    }
}
