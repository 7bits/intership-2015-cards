package it.sevenbits.cards.service;

import de.neuland.jade4j.Jade4J;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.validation.Sha;
import it.sevenbits.cards.web.domain.forms.DiscountByCampaignForm;
import it.sevenbits.cards.web.domain.forms.FeedbackForm;
import it.sevenbits.cards.web.domain.forms.SendForm;
import it.sevenbits.cards.web.utils.DomainResolver;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    DomainResolver domainResolver;

    private static Sender sender = new Sender();

    private Logger LOG = Logger.getLogger(NotificationService.class);

    @Async
    public void sendActivation(User user) throws ServiceException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("domainString", domainResolver.getDomain());
        model.put("activationHash", user.getAccountHash());
        try {
            URL url = this.getClass().getResource("../../../../../resources/templates/mail/activationmail.jade");
            String html = Jade4J.render(url, model);
            sender.send("Активация аккаунта Discounts", html, user.getUsername());
        } catch (Exception e) {
            throw new ServiceException("An error occurred while rendering mail: " + e.getMessage(), e);
        }
    }

    @Async
    public void sendFeedback(FeedbackForm feedbackForm) {
        String email = feedbackForm.getEmail();
        String title = feedbackForm.getTitle();
        String describe = feedbackForm.getDescribe();
        sender.send("Уведомление о получении письма обратной связи", "Спасибо, ваше мнение очень важно для нас. Ожидайте ответа в ближайшее время.\n", email);
        sender.send("Обращение в службу поддержки от пользователя " + email, "Заголовок: " + title + "\n" + "Источник отправки: " + email + "\n" + "Сообщение: \n" + describe, "discountfeedback@gmail.com");

    }

    @Async
    public void sendInfoAboutDiscountByCampaign(DiscountByCampaignForm discountByCampaignForm, String hash) throws ServiceException {
        Sender sender = new Sender();
        LOG.info("отправляю скидку из кампании");
        sender.send("Уведомление о получении новой скидки", "<table style=\"border-collapse: collapse;font-family: Arial\"><tr style=\"width: 440px\"><td><img src=\"http://i.imgur.com/yM3Q1N3.png\"/></td><td/></tr><tr class=\"background-main\" style=\"width: 440px;background-color: #DCDFE6;text-align: center\"><td class=\"background-main-data\" style=\"border-top-left-radius: 5px;border-top-right-radius: 5px\"><h2><span class=\"welcome-header\" style=\"font-weight: bold;color: #4D64AC\">Здравствуйте!</span></h2><span class=\"thanks\">Вам пришла скидка по акции &nbsp;<span class=\"campname\" style=\"font-weight: bold\"></span>,<br/> которой вы воспользуетесь самостоятельно <br/>или поделитесь с другим пользователем.<p>Нажмите на кнопку ниже, чтобы<br/>узнать подробнее о скидке.<br/> сервиса прямо сейчас</p></span><p/><a href=\"http://discounts.7bits.it/discount_info/?hash=" + hash + "\"><img src=\"http://i.imgur.com/vBNcLGP.png\"/></a><p/></td></tr><tr class=\"back-main-second\" style=\"width: 440px;background-color: #DCDFE6;border-bottom-right-radius: 5px;border-bottom-left-radius: 5px;text-align: right\"><td class=\"main-content\" style=\"display: block;width: 380px\"><img class=\"infoinline\" src=\"http://i.imgur.com/aMirD7P.png\"/>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"feedback\" style=\"text-align: left;display: inline-block;float: right\">Если у вас возникнут сложности<br/> c работой нашего сервиса, пожалуйста, <br/> обращайтесь в <a href=\"http://discounts.7bits.it/feedback\" class=\"fb_href\" style=\"color: #8796c6;text-decoration: underline\"> службу поддержки</a>,<br/>мы всегда рады вам помочь!<span><br/><br/></span></span></td></tr></table>", discountByCampaignForm.getEmail());
    }

    @Async
    public void notificateSend(SendForm sendForm, String hash) throws ServiceException {
        Sender sender = new Sender();
        sender.send("Уведомление о получении новой скидки", "<table style=\"border-collapse: collapse;font-family: Arial\"><tr style=\"width: 440px\"><td><img src=\"http://i.imgur.com/yM3Q1N3.png\"/></td><td/></tr><tr class=\"background-main\" style=\"width: 440px;background-color: #DCDFE6;text-align: center\"><td class=\"background-main-data\" style=\"border-top-left-radius: 5px;border-top-right-radius: 5px\"><h2><span class=\"welcome-header\" style=\"font-weight: bold;color: #4D64AC\">Здравствуйте!</span></h2><span class=\"thanks\">Поздравляем, с вами поделились скидкой!<p>Нажмите на кнопку ниже, чтобы узнать<br/> подробнее о скидке или воспользоваться ей.</p></span><p/><a href=\"http://discounts.7bits.it/discount_info/?hash=" + hash + "\"><img src=\"http://i.imgur.com/1hO0tFE.png\"/></a><p/></td></tr><tr class=\"back-main-second\" style=\"width: 440px;background-color: #DCDFE6;border-bottom-right-radius: 5px;border-bottom-left-radius: 5px;text-align: right\"><td class=\"main-content\" style=\"display: block;width: 380px\"><img class=\"infoinline\" src=\"http://i.imgur.com/aMirD7P.png\"/>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"feedback\" style=\"text-align: left;display: inline-block;float: right\">Если у вас возникнут сложности<br/> c работой нашего сервиса, пожалуйста, <br/> обращайтесь в <a href=\"http://discounts.7bits.it/feedback\" class=\"fb_href\" style=\"color: #8796c6;text-decoration: underline\"> службу поддержки</a>,<br/>мы всегда рады вам помочь!<span><br/><br/></span></span></td></tr></table>", sendForm.getEmail());
    }
}

