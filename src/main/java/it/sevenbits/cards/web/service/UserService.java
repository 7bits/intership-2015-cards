package it.sevenbits.cards.web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.validation.EmailValidation;
import it.sevenbits.cards.web.domain.RegistrationForm;
@Service
public class UserService {
    @Autowired

    @Qualifier(value = "userRepository")
    private UserRepository repository;

    public void createUser(RegistrationForm form) throws ServiceException {
        final User user = new User();
        String email = form.getEmail();
        String pswd = form.getPassword();
        String confPswd = form.getConfirmPassword();
        StringBuilder stringBuilder = new StringBuilder();
        boolean valid = true;
        if (EmailValidation.checkEmailValidity(email)) {
            user.setEmail(email);
        } else {
            stringBuilder.append("not validity email\n");
            valid=false;
        }
        if (pswd == confPswd) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(pswd));
        } else {
            stringBuilder.append("password doesn't equal confirm password\n");
            valid = false;
        }
        user.setIsStore(false);
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