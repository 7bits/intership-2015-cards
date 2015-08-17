package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.domain.PasswordRestore;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.core.repository.RestorePasswordRepository;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.validation.Sha;
import it.sevenbits.cards.web.domain.NewPasswordForm;
import it.sevenbits.cards.web.domain.PasswordRestoreForm;
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
            sender.send("Восстановление пароля Discounts", "Ссылка для восстановления пароля:\n" +
                    "http://discounts.7bits.it/password_restore/?hash=" + restore.getHash() +
                    "\n Для отмены операции перейдите по ссылке:\n" +
                    "http://discounts.7bits.it/password_restore/?hash=delete"
                    + restore.getHash(), restore.getEmail());
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
