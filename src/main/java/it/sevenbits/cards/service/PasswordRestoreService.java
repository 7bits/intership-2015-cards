package it.sevenbits.cards.service;

import de.neuland.jade4j.Jade4J;
import it.sevenbits.cards.core.domain.PasswordRestore;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.RestorePasswordRepository;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.validation.Sha;
import it.sevenbits.cards.web.domain.forms.NewPasswordForm;
import it.sevenbits.cards.web.domain.forms.PasswordRestoreForm;
import it.sevenbits.cards.web.utils.DomainResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PasswordRestoreService {
    @Autowired
    @Qualifier(value = "restorePasswordRepository")
    private RestorePasswordRepository restorePasswordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    DomainResolver domainResolver;

    private static Sender sender = new Sender();

    Logger LOG = Logger.getLogger(UserService.class);

    User user = new User();

    public void printHash(String hash) {
        LOG.info(hash);
    }

    public void restorePassword(NewPasswordForm form) throws ServiceException {
        String newPassword = form.getPassword();
        String hash = form.getHash();
        String email = null;
        try {
            email = restorePasswordRepository.findEmailByHash(hash);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding email by hash: " + e.getMessage(), e);
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        try {
            restorePasswordRepository.setNewPassword(email, encoder.encode(newPassword));
        } catch (Exception e) {
            throw new ServiceException("An error occurred while setting new password: " + e.getMessage(), e);
        }
        try {
            restorePasswordRepository.delete(email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while deleting by email: " + e.getMessage(), e);
        }
    }

    public PasswordRestore generateHash(PasswordRestoreForm form) throws ServiceException {
        try {
            user = userRepository.findByUsername(form.getEmail());
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding user by email(username): " + e.getMessage(), e);
        }
        PasswordRestore passwordRestore = new PasswordRestore();
        passwordRestore.setEmail(form.getEmail());
        try {
            passwordRestore.setHash(Sha.hash256());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String hash = null;
        try {
            hash = restorePasswordRepository.findHashByEmail(passwordRestore.getEmail());
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding hash by email: " + e.getMessage(), e);
        }
        if (hash == null) {
            try {
                restorePasswordRepository.save(passwordRestore);
            } catch (Exception e) {
                throw new ServiceException("An error occurred while saving hash and email: " + e.getMessage(), e);
            }
        } else {
            try {
                restorePasswordRepository.updateHash(passwordRestore.getHash(), passwordRestore.getEmail());
            } catch (Exception e) {
                throw new ServiceException("An error occurred while updating hash: " + e.getMessage(), e);
            }
        }
        return passwordRestore;
    }
    @Async
    public void sendEmail(PasswordRestore restore) throws ServiceException {
        if (restore == null) {
            LOG.error("User doesn't exist");
        } else {
            Map<String, Object> model = new HashMap<String, Object>();

            model.put("restoreHash", restore.getHash());
            model.put("domainString",domainResolver.getDomain());
            try {
                URL url = this.getClass().getResource("../../../../../resources/templates/mail/passwordRestoreMail.jade");
                String html = Jade4J.render(url, model);
                sender.send("Уведомление о получении новой скидки",html,restore.getEmail());
            }catch (IOException e){
                e.printStackTrace();
            }
            sender.send("Восстановление пароля Discounts","<table style=\"border-collapse: collapse;font-family: Arial;\">\n" +
                    "<tr style=\"width: 440px;\"><td><img src=\"http://i.imgur.com/yM3Q1N3.png\"></td><td></td></tr>\n" +
                    "<tr class=\"background-main\" style=\"width: 440px;background-color: #DCDFE6;text-align: center;\"><td class=\"background-main-data\" style=\"border-top-left-radius: 5px;border-top-right-radius: 5px;\"><h2><span class=\"welcome-header\" style=\"font-weight: bold;color: #4D64AC;\">Здравствуйте!</span></h2><span class=\"thanks\"> Вы отправили запрос на восстановление<br>пароля вашего аккаунта<p>Для того, чтобы задать новый пароль,<br>нажмите на кнопку ниже</p></span><p></p><a href=\"http://discounts.7bits.it/password_restore/?hash="+restore.getHash()+"\"><img src=\"http://i.imgur.com/XPtKMmq.png\"></a><p></p><p>Пожалуйста, проигнорируйте данное письмо,<br> если оно попало к Вам по ошибке.</p>\n" +
                    "</td></tr><tr class=\"back-main-second\" style=\"width: 440px;background-color: #DCDFE6;border-bottom-right-radius: 5px;border-bottom-left-radius: 5px;text-align: right;\"><td class=\"main-content\" style=\"display: block;width: 380px;\"><img class=\"infoinline\" src=\"http://i.imgur.com/aMirD7P.png\">&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"feedback\" style=\"text-align: left;display: inline-block;float: right;\">Если у вас возникнут сложности<br> c работой нашего сервиса, пожалуйста, <br> обращайтесь в <a href=\"http://discounts.7bits.it/feedback\" class=\"fb_href\" style=\"color: #8796c6;text-decoration: underline;\"> службу поддержки</a>,<br>мы всегда рады вам помочь!<span><br><br></span></span></td></tr></table>",restore.getEmail());
//            sender.send("Восстановление пароля Discounts", "Ссылка для восстановления пароля:\n" +
//                    "http://discounts.7bits.it/password_restore/?hash=" + restore.getHash() +
//                    "\n Для отмены операции перейдите по ссылке:\n" +
//                    "http://discounts.7bits.it/password_restore/?hash=delete"
//                    + restore.getHash(), restore.getEmail());
        }
    }
    public void deleteByHash(final String hash) throws ServiceException{
        String email = null;
        try {
            email = restorePasswordRepository.findEmailByHash(hash.substring(6));
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding email by hash: " + e.getMessage(), e);
        }
        try {
            restorePasswordRepository.delete(email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while deleting hash by email: " + e.getMessage(), e);
        }
    }
}
