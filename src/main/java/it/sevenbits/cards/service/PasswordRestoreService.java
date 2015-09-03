package it.sevenbits.cards.service;

import it.sevenbits.cards.core.domain.PasswordRestore;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.core.repository.RestorePasswordRepository;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.validation.Sha;
import it.sevenbits.cards.web.domain.forms.NewPasswordForm;
import it.sevenbits.cards.web.domain.forms.PasswordRestoreForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;

/**
 * Created by taro on 27.07.15.
 */
@Service
public class PasswordRestoreService {
    @Autowired
    @Qualifier(value = "restorePasswordRepository")
    private RestorePasswordRepository repository;

    @Autowired
    private UserRepository userRepository;

    private static Sender sender = new Sender();

    Logger LOG = Logger.getLogger(UserService.class);

    User user = new User();

    public void printHash(String hash) {
        LOG.info(hash);
    }

    public void restorePassword(NewPasswordForm form) throws ServiceException{
        String newPassword = form.getPassword();
        String hash = form.getHash();
        String email = null;
        try {
            email = repository.findEmailByHash(hash);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        try {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            repository.setNewPassword(email, encoder.encode(newPassword));
            try {
                repository.delete(email);
            } catch (RepositoryException e) {
                LOG.error("delete() service error");
            }
        } catch (RepositoryException e) {
            LOG.error("setNewPassword() service error");
        }
    }

    public PasswordRestore generateHash(PasswordRestoreForm form) throws ServiceException{
        try {
            user = userRepository.findByUsername(form.getEmail());
        } catch (RepositoryException e) {
            LOG.error("user doesn't exist1");
        }
        if (user == null) {
            LOG.error("user doesn't exist");
            return null;
        } else {
            PasswordRestore restore = new PasswordRestore();
            restore.setEmail(form.getEmail());
            try {
                restore.setHash(Sha.hash256());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            try {
                if(repository.findHashByEmail(restore.getEmail()) == null) {
                    repository.save(restore);
                } else {
                    repository.updateHash(restore.getHash(), restore.getEmail());
                }
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
            return restore;
        }
    }
    @Async
    public void sendEmail(PasswordRestore restore) throws ServiceException {
        if (restore == null) {
            LOG.error("User doesn't exist");
        } else {
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
    public void deleteByHash(final String hash) {
        String email = null;
        try {
            email = repository.findEmailByHash(hash.substring(6));
        } catch (RepositoryException e) {
            LOG.error("WTF?!?!?!");
        }
        try {
            repository.delete(email);
        } catch (RepositoryException e) {
            LOG.error("WTF?!");
        }
    }
}
