package it.sevenbits.cards.web.service;

import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.web.domain.DiscountByCampaignForm;
import it.sevenbits.cards.web.domain.SendForm;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
/**
 * Created by deamor on 11.08.15.
 */
@Service
public class NewDiscountNotificationService {
    private Logger LOG = Logger.getLogger(NewDiscountNotificationService.class);
    @Async
    public void notificateCreate(DiscountByCampaignForm form){
        Sender sender = new Sender();
        sender.send("Уведомление о получении новой скидки", "Пожалуйста проверьте свой аккаунт:\n" +
                "http://discounts.7bits.it/personal_area" +
                "\n У вас появилась новая скидка. \n" +
                "Информация о скидке: \n" +
                "Акция: " + form.getName() + "\n" +
                "Подробности: " + form.getDescription() + "\n" +
                "Процент: " + form.getPercent()
                , form.getEmail());
    }
    @Async
    public void notificateSend(SendForm form){
        Sender sender = new Sender();
        sender.send("Уведомление о получении новой скидки", "Пожалуйста проверьте свой аккаунт:\n" +
                "http://discounts.7bits.it/personal_area" +
                "\n Кто-то поделился с вами своей скидкой. \n" +
                "Информация о скидке: \n" +
                "Идентификационный номер(UIN): " + form.getUin() + "\n"
                , form.getEmail());
    }
}
