package it.sevenbits.cards.web.service;


import it.sevenbits.cards.core.repository.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.UserPersistRepository;
import it.sevenbits.cards.validation.EmailValidation;
import it.sevenbits.cards.web.domain.RegistrationForm;

import org.apache.log4j.Logger;

@Service
public class UserService {
    @Autowired

    @Qualifier(value = "userPersistRepository")
    private UserPersistRepository repository;
    Logger LOG = Logger.getLogger(UserService.class);

    public void createUser(RegistrationForm form) throws ServiceException, RepositoryException {
        final User user = new User();
        User userExist = null;
        String email = form.getEmail();
        String pswd = form.getPassword();
        String confPswd = form.getConfirmPassword();
        boolean valid = true;
        if (EmailValidation.checkEmailValidity(email)) {
            userExist = repository.findByUsername(email);
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
        user.setUserId("83HFd");
        if (valid) {
            try {
               repository.save(user);
            } catch (Exception e) {
                throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
            }
        }
    }
}