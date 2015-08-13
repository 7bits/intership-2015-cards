package it.sevenbits.cards.web.service;


import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.web.domain.FeedbackForm;
import it.sevenbits.cards.web.domain.PasswordRestoreForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.validation.EmailValidation;
import it.sevenbits.cards.web.domain.RegistrationForm;

import org.apache.log4j.Logger;

@Service
public class UserService {
    @Autowired

    @Qualifier(value = "userRepository")
    private UserRepository repository;
    Logger LOG = Logger.getLogger(UserService.class);

    private static Sender sender = new Sender();

    public String findUserIdByUserName(String userName) throws ServiceException {
        try {
            return repository.findUserIdByUserName(userName);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding UserId by User Name discount: " + e.getMessage(), e);
        }
    }

    public void createUser(RegistrationForm form) throws ServiceException {
        final User user = new User();
        user.setEmail(form.getEmail());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(form.getPassword()));
        String maxUserId;
        try {
            maxUserId = repository.maxUserId();
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding by User Name: " + e.getMessage(), e);
        }
        if (maxUserId == null) {
            user.setUserId("1000");
        } else {
            String userId = maxUserId.substring(0, maxUserId.length() - 1);
            boolean again = true;
            char ch = ' ';
            for (int i = 1; i <= maxUserId.length() && again; i++) {
                again = false;
                ch = maxUserId.charAt(maxUserId.length() - i);

                if ((ch >= '0' && ch < '9') || (ch >= 'A' && ch < 'Z') || (ch >= 'a' && ch < 'z')) {
                    ch = (char) ((int) ch + 1);
                } else if (ch == '9') {
                    ch = 'A';
                } else if (ch == 'Z') {
                    ch = 'a';
                } else if (ch == 'z') {
                    if (i == maxUserId.length()) {
                        maxUserId = "0" + maxUserId;
                    }
                    ch = '0';
                    again = true;
                }
            }
            userId = userId + Character.toString(ch);
            user.setUserId(userId);
        }
        try {
            repository.save(user);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }
    public void changeUserRoleByUserId(String userRole, String userId) throws ServiceException {
        try {
            repository.changeUserRoleByUserId(userRole, userId);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding UserId by User Name discount: " + e.getMessage(), e);
        }
    }

    public void enableUserByEmail(String email) throws ServiceException {
        try {
            repository.enableUserByEmail(email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while changing User enabled property to true: " + e.getMessage(), e);
        }
    }
    @Async
    public void sendMailToFeedback(FeedbackForm form) {
        String email = form.getEmail();
        String title = form.getTitle();
        String describe = form.getDescribe();
        sender.send(title, "Спасибо, ваше мнение очень важно для нас. Ожидайте ответы в ближайшее время.\n", email);
    }
}