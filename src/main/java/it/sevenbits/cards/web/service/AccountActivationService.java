package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.domain.AccountActivation;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.AccountActivationRepository;
import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.validation.Sha;
import it.sevenbits.cards.web.domain.RegistrationForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
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


    public AccountActivation generateActivationHash(RegistrationForm form) throws ServiceException{
        try {
            user = userRepository.findByUsername(form.getEmail());
        } catch (RepositoryException e) {
            LOG.error("user doesn't exist1");
        }
        if (user == null) {
            LOG.error("user doesn't exist");
            return null;
        } else {
            AccountActivation activation = new AccountActivation();
            activation.setEmail(form.getEmail());
            try {
                activation.setHash(Sha.hash256());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            try {
                if(activationRepository.findHashByEmail(activation.getEmail()) == null) {
                    activationRepository.save(activation);
                } else {
                    activationRepository.updateHash(activation.getHash(), activation.getEmail());
                }
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
            return activation;
        }
    }
    @Async
    public void sendEmail(AccountActivation accountActivation) throws ServiceException {
        if (accountActivation == null) {
            LOG.error("User doesn't exist");
        } else {
            sender.send("Активация аккаунта Discounts", "<table style=\"border-collapse: collapse; font-family: Arial\"><tr style=\"width: 440px\"><td><img src=\"http://i.imgur.com/yM3Q1N3.png\"/></td><td/></tr><tr class=\"background-main\" style=\"width: 440px;background-color: #DCDFE6;text-align: center\"><td class=\"background-main-data\" style=\"border-top-left-radius: 5px;border-top-right-radius: 5px\"><h2><span class=\"welcome-header\" style=\"font-family: Arial Bold;color: #4D64AC\">Добро пожаловать на DISCOUNT!</span></h2><span class=\"thanks\" style=\"font-weight: bold\"> Cпасибо, что присоединились к нам!<p>Начните экономить с помощью нашего<br/> сервиса прямо сейчас</p></span><p/><a href=\"http://localhost:9000/registration/?hash="+accountActivation.getHash()+"\"><img src=\"http://i.imgur.com/PFnXAs1.png\"/></a><p/></td></tr><tr class=\"back-main-second\" style=\"width: 440px;background-color: #DCDFE6;border-bottom-right-radius: 5px;border-bottom-left-radius: 5px;text-align: right\"><td style=\"display: block;width: 400px\"><img class=\"infoinline\" src=\"http://i.imgur.com/KzboToI.png\"/>&nbsp;&nbsp;<span class=\"feedback\" style=\"text-align: left;display: inline-block;float: right\">Если у вас возникнут сложности<br/> c работой нашего сервиса, пожалуйста, <br/> обращайтесь в <a href=\"http://discounts.7bits.it/feedback\" class=\"fb_href\" style=\"color: #8796c6;text-decoration: underline\"> службу поддержки</a>,<br/>мы всегда рады вам помочь!<span><br/><br/></span></span></td></tr></table>", accountActivation.getEmail());
//            sender.send("Активация аккаунта Discounts", "Ссылка для активации:\n" +
//                    "http://discounts.7bits.it/registration/?hash=" + accountActivation.getHash(), accountActivation.getEmail());
        }
    }
    public void activateByHash(String hash) {
        String email = null;
        try {
            email = activationRepository.findEmailByHash(hash);
        } catch (RepositoryException e) {
            LOG.error("Activation finding email by hash exception.");
        }
        try {
            LOG.info(email);
            activationRepository.delete(email);
            userRepository.enableUserByEmail(email);
        } catch (RepositoryException e) {
            LOG.error("Activation deleting exception");
        }
    }
    public String findEmailByHash(String hash) throws RepositoryException {
        if (hash == null) {
            throw new RepositoryException("hash is empty");
        }
        try {
            return activationRepository.findEmailByHash(hash);
        } catch (Exception e) {
            throw new RepositoryException("Find email by hash database error" + e.getMessage(), e);
        }
    }

}
