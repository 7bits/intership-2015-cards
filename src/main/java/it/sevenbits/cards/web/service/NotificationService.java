package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.domain.DiscountHash;
import it.sevenbits.cards.core.repository.DiscountHashRepository;
import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.validation.Sha;
import it.sevenbits.cards.web.domain.DiscountByCampaignForm;
import it.sevenbits.cards.web.domain.SendForm;
import org.apache.commons.lang3.text.WordUtils;
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
        LOG.info("отправляю скидку из кампании");
        sender.send("Уведомление о получении новой скидки","<table style=\"border-collapse: collapse;font-family: Arial\"><tr style=\"width: 440px\"><td><img src=\"http://i.imgur.com/yM3Q1N3.png\"/></td><td/></tr><tr class=\"background-main\" style=\"width: 440px;background-color: #DCDFE6;text-align: center\"><td class=\"background-main-data\" style=\"border-top-left-radius: 5px;border-top-right-radius: 5px\"><h2><span class=\"welcome-header\" style=\"font-weight: bold;color: #4D64AC\">Здравствуйте!</span></h2><span class=\"thanks\">Вам пришла скидка по акции &nbsp;<span class=\"campname\" style=\"font-weight: bold\">"+ WordUtils.capitalizeFully(discountByCampaignForm.getName())+"</span>,<br/> которой вы воспользуетесь самостоятельно <br/>или поделитесь с другим пользователем.<p>Нажмите на кнопку ниже, чтобы<br/>узнать подробнее о скидке.<br/> сервиса прямо сейчас</p></span><p/><a href=\"http://localhost:9000/discount_info/?hash="+discountHash.getHash()+"\"><img src=\"http://i.imgur.com/vBNcLGP.png\"/></a><p/></td></tr><tr class=\"back-main-second\" style=\"width: 440px;background-color: #DCDFE6;border-bottom-right-radius: 5px;border-bottom-left-radius: 5px;text-align: right\"><td class=\"main-content\" style=\"display: block;width: 380px\"><img class=\"infoinline\" src=\"http://i.imgur.com/aMirD7P.png\"/>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"feedback\" style=\"text-align: left;display: inline-block;float: right\">Если у вас возникнут сложности<br/> c работой нашего сервиса, пожалуйста, <br/> обращайтесь в <a href=\"http://discounts.7bits.it/feedback\" class=\"fb_href\" style=\"color: #8796c6;text-decoration: underline\"> службу поддержки</a>,<br/>мы всегда рады вам помочь!<span><br/><br/></span></span></td></tr></table>", discountByCampaignForm.getEmail());
//        sender.send("Уведомление о получении новой скидки", "Пожалуйста проверьте свой аккаунт:\n" +
//                "http://discounts.7bits.it/personal_area" +
//                "\n У вас появилась новая скидка. \n" +
//                "Информация о скидке: \n" +
//                "Акция: " + discountByCampaignForm.getName() + "\n" +
//                "Подробности: " + discountByCampaignForm.getDescription() + "\n" +
//                "Процент: " + discountByCampaignForm.getPercent() + "\n" +
//                "http://discounts.7bits.it/discount_info/?hash=" + discountHash.getHash() + "\n"
//                , discountByCampaignForm.getEmail());
    }
    @Async
    public void notificateCreateIfExist(DiscountByCampaignForm discountByCampaignForm, Long id) throws ServiceException{
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
        LOG.info("отправляю скидку из кампании");
        sender.send("Уведомление о получении новой скидки","<table style=\"border-collapse: collapse;font-family: Arial\"><tr style=\"width: 440px\"><td><img src=\"http://i.imgur.com/yM3Q1N3.png\"/></td><td/></tr><tr class=\"background-main\" style=\"width: 440px;background-color: #DCDFE6;text-align: center\"><td class=\"background-main-data\" style=\"border-top-left-radius: 5px;border-top-right-radius: 5px\"><h2><span class=\"welcome-header\" style=\"font-weight: bold;color: #4D64AC\">Здравствуйте!</span></h2><span class=\"thanks\">Вам пришла скидка по акции &nbsp;<span class=\"campname\" style=\"font-weight: bold\">"+ WordUtils.capitalizeFully(discountByCampaignForm.getName())+"</span>,<br/> которой вы воспользуетесь самостоятельно <br/>или поделитесь с другим пользователем.<p>Нажмите на кнопку ниже, чтобы<br/>узнать подробнее о скидке.<br/> сервиса прямо сейчас</p></span><p/><a href=\"http://localhost:9000/personal_area\"><img src=\"http://i.imgur.com/vBNcLGP.png\"/></a><p/></td></tr><tr class=\"back-main-second\" style=\"width: 440px;background-color: #DCDFE6;border-bottom-right-radius: 5px;border-bottom-left-radius: 5px;text-align: right\"><td class=\"main-content\" style=\"display: block;width: 380px\"><img class=\"infoinline\" src=\"http://i.imgur.com/aMirD7P.png\"/>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"feedback\" style=\"text-align: left;display: inline-block;float: right\">Если у вас возникнут сложности<br/> c работой нашего сервиса, пожалуйста, <br/> обращайтесь в <a href=\"http://discounts.7bits.it/feedback\" class=\"fb_href\" style=\"color: #8796c6;text-decoration: underline\"> службу поддержки</a>,<br/>мы всегда рады вам помочь!<span><br/><br/></span></span></td></tr></table>", discountByCampaignForm.getEmail());
//        sender.send("Уведомление о получении новой скидки", "Пожалуйста проверьте свой аккаунт:\n" +
//                "http://discounts.7bits.it/personal_area" +
//                "\n У вас появилась новая скидка. \n" +
//                "Информация о скидке: \n" +
//                "Акция: " + discountByCampaignForm.getName() + "\n" +
//                "Подробности: " + discountByCampaignForm.getDescription() + "\n" +
//                "Процент: " + discountByCampaignForm.getPercent() + "\n" +
//                "http://discounts.7bits.it/discount_info/?hash=" + discountHash.getHash() + "\n"
//                , discountByCampaignForm.getEmail());
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
        sender.send("Уведомление о получении новой скидки", "<table style=\"border-collapse: collapse;font-family: Arial\"><tr style=\"width: 440px\"><td><img src=\"http://i.imgur.com/yM3Q1N3.png\"/></td><td/></tr><tr class=\"background-main\" style=\"width: 440px;background-color: #DCDFE6;text-align: center\"><td class=\"background-main-data\" style=\"border-top-left-radius: 5px;border-top-right-radius: 5px\"><h2><span class=\"welcome-header\" style=\"font-weight: bold;color: #4D64AC\">Здравствуйте!</span></h2><span class=\"thanks\">Поздравляем, с вами поделились скидкой!<p>Нажмите на кнопку ниже, чтобы узнать<br/> подробнее о скидке или воспользоваться ей.</p></span><p/><a href=\"http://localhost:9000/discount_info/?hash="+discountHash.getHash()+"\"><img src=\"http://i.imgur.com/1hO0tFE.png\"/></a><p/></td></tr><tr class=\"back-main-second\" style=\"width: 440px;background-color: #DCDFE6;border-bottom-right-radius: 5px;border-bottom-left-radius: 5px;text-align: right\"><td class=\"main-content\" style=\"display: block;width: 380px\"><img class=\"infoinline\" src=\"http://i.imgur.com/aMirD7P.png\"/>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"feedback\" style=\"text-align: left;display: inline-block;float: right\">Если у вас возникнут сложности<br/> c работой нашего сервиса, пожалуйста, <br/> обращайтесь в <a href=\"http://discounts.7bits.it/feedback\" class=\"fb_href\" style=\"color: #8796c6;text-decoration: underline\"> службу поддержки</a>,<br/>мы всегда рады вам помочь!<span><br/><br/></span></span></td></tr></table>", sendForm.getEmail());
//        sender.send("Уведомление о получении новой скидки", "Пожалуйста проверьте свой аккаунт:\n" +
//                "http://discounts.7bits.it/personal_area" +
//                "\n Кто-то поделился с вами своей скидкой. \n" +
//                "Информация о скидке: \n" +
//                "Идентификационный номер(UIN): " + sendForm.getUin() + "\n" +
//                "http://discounts.7bits.it/welcome/?hash=" + discountHash.getHash() + "\n"
//                , sendForm.getEmail());
    }

    @Async
    public void notificateSendIfExist(SendForm sendForm, Long id) throws ServiceException{
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
        sender.send("Уведомление о получении новой скидки","<table style=\"border-collapse: collapse;font-family: Arial\"><tr style=\"width: 440px\"><td><img src=\"http://i.imgur.com/yM3Q1N3.png\"/></td><td/></tr><tr class=\"background-main\" style=\"width: 440px;background-color: #DCDFE6;text-align: center\"><td class=\"background-main-data\" style=\"border-top-left-radius: 5px;border-top-right-radius: 5px\"><h2><span class=\"welcome-header\" style=\"font-weight: bold;color: #4D64AC\">Здравствуйте!</span></h2><span class=\"thanks\">Поздравляем, с вами поделились скидкой!<p>Нажмите на кнопку ниже, чтобы узнать<br/> подробнее о скидке или воспользоваться ей.</p></span><p/><a href=\"http://localhost:9000/personal_area\"><img src=\"http://i.imgur.com/1hO0tFE.png\"/></a><p/></td></tr><tr class=\"back-main-second\" style=\"width: 440px;background-color: #DCDFE6;border-bottom-right-radius: 5px;border-bottom-left-radius: 5px;text-align: right\"><td class=\"main-content\" style=\"display: block;width: 380px\"><img class=\"infoinline\" src=\"http://i.imgur.com/aMirD7P.png\"/>&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"feedback\" style=\"text-align: left;display: inline-block;float: right\">Если у вас возникнут сложности<br/> c работой нашего сервиса, пожалуйста, <br/> обращайтесь в <a href=\"http://discounts.7bits.it/feedback\" class=\"fb_href\" style=\"color: #8796c6;text-decoration: underline\"> службу поддержки</a>,<br/>мы всегда рады вам помочь!<span><br/><br/></span></span></td></tr></table>",sendForm.getEmail());
//        sender.send("Уведомление о получении новой скидки", "Пожалуйста проверьте свой аккаунт:\n" +
//                "http://discounts.7bits.it/personal_area" +
//                "\n Кто-то поделился с вами своей скидкой. \n" +
//                "Информация о скидке: \n" +
//                "Идентификационный номер(UIN): " + sendForm.getUin() + "\n" +
//                "http://discounts.7bits.it/welcome/?hash=" + discountHash.getHash() + "\n"
//                , sendForm.getEmail());
    }
}
