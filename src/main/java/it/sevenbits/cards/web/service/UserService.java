package it.sevenbits.cards.web.service;


import it.sevenbits.cards.core.repository.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    public String findUserIdByUserName(String userName) throws ServiceException {
        try {
            return repository.findUserIdByUserName(userName);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding UserId by User Name discount: " + e.getMessage(), e);
        }
    }

    public void createUser(RegistrationForm form) throws ServiceException {
        final User user = new User();
        User userExist = null;
        String email = form.getEmail();
        String pswd = form.getPassword();
        String confPswd = form.getConfirmPassword();
        boolean valid = true;
        if (EmailValidation.checkEmailValidity(email)) {
            try {
                userExist = repository.findByUsername(email);
            } catch (Exception e) {
                throw new ServiceException("An error occurred while finding by User Name: " + e.getMessage(), e);
            }
            if (userExist == null) {
                user.setEmail(email);
            } else {
                valid=false;
                LOG.error("user already exist");
            }
        } else {
            LOG.error("not validity email\n");
            valid=false;
        }
        if (pswd.equals(confPswd)) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(pswd));
        } else {
            LOG.error("password doesn't equal confirm password\n");
            valid = false;
        }
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
        if (valid) {
            try {
               repository.save(user);
            } catch (Exception e) {
                throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
            }
        }
    }
}