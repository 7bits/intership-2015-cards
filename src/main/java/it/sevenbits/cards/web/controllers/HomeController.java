package it.sevenbits.cards.web.controllers;

import it.sevenbits.cards.web.domain.BindForm;
import it.sevenbits.cards.web.domain.SendForm;
import it.sevenbits.cards.web.domain.UseForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
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
}