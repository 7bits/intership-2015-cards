package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.domain.DiscountHash;
import it.sevenbits.cards.core.repository.DiscountHashRepository;
import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.validation.Sha;
import it.sevenbits.cards.web.domain.DiscountByCampaignForm;
import it.sevenbits.cards.web.domain.SendForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * Created by deamor on 11.08.15.
 */
@Service
public class NotificationService {
    @Autowired
    private DiscountHashRepository discountHashRepository;

    private Logger LOG = Logger.getLogger(NotificationService.class);
    @Async
    public void notificateCreate(DiscountByCampaignForm discountByCampaignForm, Long id) throws ServiceException{
        DiscountHash discountHash = new DiscountHash();
        discountHash.setDiscountId(id);
        try {
            discountHash.setHash(Sha.hash256());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }try {
            discountHashRepository.save(discountHash);
        } catch (Exception e) {
            throw new ServiceException("An error while saving discountHash: " + e.getMessage(), e);
        }
        Sender sender = new Sender();
        sender.send("Уведомление о получении новой скидки", "Пожалуйста проверьте свой аккаунт:\n" +
                "http://discounts.7bits.it/personal_area" +
                "\n У вас появилась новая скидка. \n" +
                "Информация о скидке: \n" +
                "Акция: " + discountByCampaignForm.getName() + "\n" +
                "Подробности: " + discountByCampaignForm.getDescription() + "\n" +
                "Процент: " + discountByCampaignForm.getPercent() + "\n" +
                "http://discounts.7bits.it/welcome/?hash=" + discountHash.getHash() + "\n"
                , discountByCampaignForm.getEmail());
    }
    @Async
    public void notificateSend(SendForm sendForm, Long id) throws ServiceException{
        DiscountHash discountHash = new DiscountHash();
        discountHash.setDiscountId(id);
        try {
            discountHash.setHash(Sha.hash256());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }try {
            discountHashRepository.save(discountHash);
        } catch (Exception e) {
            throw new ServiceException("An error while saving discountHash: " + e.getMessage(), e);
        }
        Sender sender = new Sender();
        sender.send("Уведомление о получении новой скидки", "Пожалуйста проверьте свой аккаунт:\n" +
                "http://discounts.7bits.it/personal_area" +
                "\n Кто-то поделился с вами своей скидкой. \n" +
                "Информация о скидке: \n" +
                "Идентификационный номер(UIN): " + sendForm.getUin() + "\n" +
                "http://discounts.7bits.it/welcome/?hash=" + discountHash.getHash() + "\n"
                , sendForm.getEmail());
    }
}
