package it.sevenbits.cards.web.service;




import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.web.domain.RegistrationForm;
import it.sevenbits.cards.web.domain.UserForm;
import it.sevenbits.cards.core.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import it.sevenbits.cards.validation.EmailValidation;

@Service
public class UsersService {
    @Autowired

    @Qualifier(value = "discountPersistRepository")
    private UsersRepository repository;

    public void saveUser(RegistrationForm form) throws ServiceException {
        final User user = new User();
        String s = form.getEmail();
        if (EmailValidation.checkEmailValidity(s)) {
            user.setEmail(form.getEmail());
        } else {
            user.setEmail("invalid email");
        }
        user.setPasswordHash(form.getPassword());
        user.setIsStore(false);
        user.setUserId("123");
        try {
            repository.saveUser(user);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }
}