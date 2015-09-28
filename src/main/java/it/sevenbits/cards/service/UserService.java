package it.sevenbits.cards.service;


import it.sevenbits.cards.core.domain.Role;
import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.validation.Sha;
import it.sevenbits.cards.web.domain.forms.*;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.UserRepository;

import org.apache.log4j.Logger;

@Service
public class UserService {

    @Autowired
    @Qualifier(value = "userRepository")
    private UserRepository userRepository;

    Logger LOG = Logger.getLogger(UserService.class);

    private static Sender sender = new Sender();

    public void updateUser(RegistrationForm form) throws ServiceException {
        final User user = new User();
        user.setEmail(form.getEmail());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(form.getPassword()));
        try {
            userRepository.update(user);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }

    public void changeUserRoleByEmail(String userRole, String email) throws ServiceException {
        try {
            userRepository.changeUserRoleByEmail(userRole, email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding UserId by User Name discount: " + e.getMessage(), e);
        }
    }

    public User findByEmail(String email) throws ServiceException {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding user by email: " + e.getMessage(), e);
        }
    }

    public void activateByHash(String hash) throws ServiceException {
        try {
            userRepository.activateByHash(hash);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while activating user by hash: " + e.getMessage(), e);
        }
    }

    public void restorePassword(NewPasswordForm newPasswordForm) throws ServiceException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordHash = passwordEncoder.encode(newPasswordForm.getPassword());
        try {
            userRepository.restorePassword(passwordHash, newPasswordForm.getHash());
        } catch (Exception e) {
            throw new ServiceException("An error occurred while restoring password: " + e.getMessage(), e);
        }
    }

    public Long findIdByHash(String hash) throws ServiceException{
        try {
            return userRepository.findIdByHash(hash);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while finding id by hash: " + e.getMessage(), e);
        }
    }

    public void createUser(String email) throws ServiceException {
        final User user = new User();
        user.setEmail(email);
        String accountHash = null;
        user.setRole(Role.ROLE_USER);
        try {
            accountHash = Sha.hash256();
        } catch (Exception e) {
            throw new ServiceException("An error occurred while generating SHA hash: " + e.getMessage(), e);
        }
        user.setAccountHash(accountHash);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }
}