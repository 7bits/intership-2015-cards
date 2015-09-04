package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.AccountActivation;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.AccountActivationRepository;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.validation.Sha;
import it.sevenbits.cards.web.domain.forms.RegistrationForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * Created by deamor on 12.08.15.
 */
@Service
public class AccountActivationService {

    @Autowired
    private AccountActivationRepository activationRepository;

    @Autowired
    private UserRepository userRepository;

    private static Sender sender = new Sender();

    Logger LOG = Logger.getLogger(AccountActivationService.class);

    User user = new User();

    public void printHash(String hash) {
        LOG.info(hash);
    }


    public AccountActivation generateActivationHash(RegistrationForm form) throws ServiceException {
        AccountActivation accountActivation = new AccountActivation();
        accountActivation.setEmail(form.getEmail());
        try {
            accountActivation.setHash(Sha.hash256());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String hash = null;
        try{
            activationRepository.findHashByEmail(accountActivation.getEmail());
        }catch (Exception e){
            throw new ServiceException("An error occurred while finding hash by email: " + e.getMessage(), e);
        }
        if (hash == null) {
            try {
                activationRepository.save(accountActivation);
            }catch (Exception e){
                throw new ServiceException("An error occurred while saving account activation: " + e.getMessage(), e);
            }
        } else {
            try {
                activationRepository.updateHash(accountActivation.getHash(), accountActivation.getEmail());
            }catch (Exception e){
                throw new ServiceException("An error occurred while updating hash: " + e.getMessage(), e);
            }
        }
        return accountActivation;
    }
    @Async
    public void sendEmail(AccountActivation accountActivation) throws ServiceException {
        if (accountActivation == null) {
            LOG.error("User doesn't exist");
        } else {
            sender.send("Активация аккаунта Discounts", "<table style=\"border-collapse: collapse;font-family: Arial;\">\n" +
                    "<tr style=\"width: 440px;\"><td><img src=\"http://i.imgur.com/yM3Q1N3.png\"></td><td></td></tr>\n" +
                    "<tr class=\"background-main\" style=\"width: 440px;background-color: #DCDFE6;text-align: center;\"><td class=\"background-main-data\" style=\"border-top-left-radius: 5px;border-top-right-radius: 5px;\"><h2><span class=\"welcome-header\" style=\"font-weight: bold;color: #4D64AC;\">Добро пожаловать на DISCOUNT!</span></h2><span class=\"thanks\"> Cпасибо, что присоединились к нам!<p>Начните экономить с помощью нашего<br> сервиса прямо сейчас</p></span><p></p><a href=\"http://discounts.7bits.it/registration/?hash=" + accountActivation.getHash() + "\"><img src=\"http://i.imgur.com/PFnXAs1.png\"></a><p></p>\n" +
                    "</td></tr><tr class=\"back-main-second\" style=\"width: 440px;background-color: #DCDFE6;border-bottom-right-radius: 5px;border-bottom-left-radius: 5px;text-align: right;\"><td class=\"main-content\" style=\"display: block;width: 380px;\"><img class=\"infoinline\" src=\"http://i.imgur.com/aMirD7P.png\">&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"feedback\" style=\"text-align: left;display: inline-block;float: right;\">Если у вас возникнут сложности<br> c работой нашего сервиса, пожалуйста, <br> обращайтесь в <a href=\"http://discounts.7bits.it/feedback\" class=\"fb_href\" style=\"color: #8796c6;text-decoration: underline;\"> службу поддержки</a>,<br>мы всегда рады вам помочь!<span><br><br></span></span></td></tr></table>", accountActivation.getEmail());
//            sender.send("Активация аккаунта Discounts", "Ссылка для активации:\n" +
//                    "http://discounts.7bits.it/registration/?hash=" + accountActivation.getHash(), accountActivation.getEmail());
        }
    }
    public void activateByHash(String hash) throws ServiceException{
        String email = null;
        try {
            email = activationRepository.findEmailByHash(hash);
        } catch (Exception e){
            throw new ServiceException("An error occurred while finding email by hash: " + e.getMessage(), e);
        }
        try {
            activationRepository.delete(email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while deleting by email: " + e.getMessage(), e);
        }
        try {
            userRepository.enableUserByEmail(email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while enabling : " + e.getMessage(), e);
        }
    }
    public String findEmailByHash(String hash) throws ServiceException {
        try {
            return activationRepository.findEmailByHash(hash);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding email by hash: " + e.getMessage(), e);
        }
    }
}
