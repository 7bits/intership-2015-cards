package it.sevenbits.cards.web.service;

import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.web.domain.DiscountByCampaignForm;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
/**
 * Created by deamor on 11.08.15.
 */
@Service
public class NewDiscountNotificationService {
    private Logger LOG = Logger.getLogger(NewDiscountNotificationService.class);

    public void notificateCreate(DiscountByCampaignForm form){
        Sender sender = new Sender();
        sender.send("Уведомление о получении новой скидки", "Пожалуйста проверьте свой аккаунт:\n" +
                "http://localhost:9000/personal_area" +
                "\n У вас появилась новая скидка. \n" +
                "Информация о скидке: \n" +
                "Акция: " + form.getName() + "\n" +
                "Подробности: " + form.getDescription() + "\n" +
                "Процент: " + form.getPercent()
                , form.getEmail());
        LOG.info(form.getName());
    }
}
