package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.web.domain.*;
import it.sevenbits.cards.web.service.DiscountsService;
import it.sevenbits.cards.web.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DiscountsController {
    @Autowired
    private DiscountsService discountsService;
    private Logger LOG = Logger.getLogger(HomeController.class);

    @RequestMapping(value = "/use_discount", method = RequestMethod.POST)
    public String use(@ModelAttribute UseForm form, final Model model) throws ServiceException {
// В запросе пришла заполненная форма. Отправим в модель этот объект и отрендерим ее на другом шаблоне.
        DiscountForm discountForm = new DiscountForm();
        discountForm.setUin(form.getUin());
        model.addAttribute("use", form);
        discountsService.delete(discountForm);
        return "redirect:/store_area";
    }
    @RequestMapping(value = "/discounts", method = RequestMethod.GET)
    public String getDiscounts(final Model model) throws ServiceException {
        model.addAttribute("discounts", discountsService.findAll());
        return "home/discounts";
    }
    @RequestMapping(value = "/add_discount", method = RequestMethod.GET)
    public String add_discount(final Model model) {
// В модель добавим новый объект формы подписки
        model.addAttribute("add", new DiscountForm());
        return "home/add_discount";
    }
    @RequestMapping(value = "/add_discount", method = RequestMethod.POST)
    public String show_discounts(@ModelAttribute DiscountForm form, final Model model) throws ServiceException {
        LOG.debug(form);
        discountsService.save(form);
        return "redirect:/discounts";
    }
    //Personal use area page
    @RequestMapping(value = "/personal_area_use", method = RequestMethod.POST)
    public String personalAreaUse(final Model model) throws ServiceException {
        model.addAttribute("discounts", discountsService.findAllDiscountsToUse());
        return "home/discountsToUse";
    }
    //Personal send area page
    @RequestMapping(value = "/personal_area_send", method = RequestMethod.POST)
    public String personalAreaSend(final Model model) throws ServiceException {
        model.addAttribute("discounts", discountsService.findAllDiscountsToSend());
        return "home/discountsToSend";
    }
}
