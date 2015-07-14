package it.sevenbits.cards.web.controllers;
import it.sevenbits.cards.core.domain.Discount;
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
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
@Controller
public class HomeController {
    @Autowired
    private DiscountsService service;
    private Logger LOG = Logger.getLogger(HomeController.class);
    //Index page
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(final Model model) {
        return "home/index";
    }
    //Authorization page
    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public String authorization(final Model model) {
        model.addAttribute("authorization", new AuthorizationForm());
        return "home/authorization";
    }
    //Repsonal use area page
    @RequestMapping(value = "/personal_area_use", method = RequestMethod.POST)
    public String personalAreaUse(final Model model) throws ServiceException {
        model.addAttribute("discounts", service.findAllDiscountsToUse());
        return "home/discountsToUse";
    }
    //Repsonal send area page
    @RequestMapping(value = "/personal_area_send", method = RequestMethod.POST)
    public String personalAreaSend(final Model model) throws ServiceException {
        model.addAttribute("discounts", service.findAllDiscountsToSend());
        return "home/discountsToSend";
    }




















    @RequestMapping(value = "/send_discount", method = RequestMethod.GET)
    public String index_from_send(final Model model) {
// В модель добавим новый объект формы подписки
        model.addAttribute("send", new SendForm());
        return "home/index";
    }
    @RequestMapping(value = "/send_discount", method = RequestMethod.POST)
    public String send(@ModelAttribute SendForm form, final Model model) {
// В запросе пришла заполненная форма. Отправим в модель этот объект и отрендерим ее на другом шаблоне.
        model.addAttribute("send", form);
        return "home/send_discount";
    }
    //Bind
    @RequestMapping(value = "/bind_discount", method = RequestMethod.GET)
    public String index_from_bind(final Model model) {
// В модель добавим новый объект формы подписки
        model.addAttribute("bind", new BindForm());
        return "home/index";
    }
    @RequestMapping(value = "/bind_discount", method = RequestMethod.POST)
    public String bind(@ModelAttribute BindForm form, final Model model) {
// В запросе пришла заполненная форма. Отправим в модель этот объект и отрендерим ее на другом шаблоне.
        model.addAttribute("bind", form);
        return "home/bind_discount";
    }
    //Use
    @RequestMapping(value = "/use_discount", method = RequestMethod.GET)
    public String index_from_use(final Model model) {
// В модель добавим новый объект формы подписки
        model.addAttribute("use", new UseForm());
        return "home/index";
    }
    @RequestMapping(value = "/use_discount", method = RequestMethod.POST)
    public String use(@ModelAttribute UseForm form, final Model model) throws ServiceException {
// В запросе пришла заполненная форма. Отправим в модель этот объект и отрендерим ее на другом шаблоне.
        DiscountForm discountForm = new DiscountForm();
        discountForm.setUin(form.getUin());
        model.addAttribute("use", form);
        service.delete(discountForm);
        return "home/use_discount";
    }
    @RequestMapping(value = "/discounts", method = RequestMethod.GET)
    public String getDiscounts(final Model model) throws ServiceException {
        model.addAttribute("discounts", service.findAll());
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
        service.save(form);
        return "redirect:/discounts";
    }
}