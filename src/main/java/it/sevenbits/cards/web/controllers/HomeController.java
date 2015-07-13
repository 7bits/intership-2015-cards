package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.web.domain.*;
import it.sevenbits.cards.web.service.DiscountsService;
import it.sevenbits.cards.web.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class HomeController {

    @Autowired
    private DiscountsService service;

    //Index
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(final Model model) {
        // В модель добавим новый объект формы подписки
        model.addAttribute("send", new SendForm());
        model.addAttribute("bind", new BindForm());
        model.addAttribute("use", new UseForm());
        return "home/index";
    }
    //Send
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
        model.addAttribute("bind", new SendForm());
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
        model.addAttribute("use", new SendForm());
        return "home/index";
    }
    @RequestMapping(value = "/use_discount", method = RequestMethod.POST)
    public String use(@ModelAttribute UseForm form, final Model model) {
        // В запросе пришла заполненная форма. Отправим в модель этот объект и отрендерим ее на другом шаблоне.
        model.addAttribute("use", form);
        return "home/use_discount";
    }

    @RequestMapping(value = "/discounts", method = RequestMethod.GET)
    @ResponseBody
    public List<DiscountModel> getSubscriptions() throws ServiceException {
        return service.findAll();
    }

    @RequestMapping(value = "/add_discount", method = RequestMethod.GET)
    public String add_discount(@ModelAttribute DiscountForm form, final Model model) throws ServiceException {
        // В модель добавим новый объект формы подписки
        service.save(form);
        model.addAttribute("add_discount", new DiscountForm());
        // Так как нет аннотации @ResponseBody, то spring будет искать шаблон по адресу home/index
        // Если шаблона не будет найдено, то вернется 404 ошибка
        return "home/add_discount";
    }
}