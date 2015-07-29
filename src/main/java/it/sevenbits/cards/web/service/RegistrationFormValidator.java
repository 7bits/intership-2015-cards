package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.web.domain.RegistrationForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationFormValidator {
    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(DiscountFormValidator.class);

    public HashMap<String, String> validate(final RegistrationForm form){
        LOG.info("SubscriptionFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(form.getEmail(), errors, "email", "Поле не должно быть пустым.");
        validator.isNotNullOrEmpty(form.getPassword(), errors, "password", "Поле не должно быть пустым.");

        validator.shorterThan(form.getEmail(), 255, errors, "email", "Поле должно быть короче 255 символов.");
        validator.shorterThan(form.getPassword(), 255, errors, "password", "Поле должно быть короче 255 символов.");

        validator.isEmail(form.getEmail(), errors, "email", "Поле должно содержать E-mail адрес.");

        validator.isUserAlreadyExist(form.getEmail(), errors, "email", "Пользователь с данным E-mail уже существует.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }
        return errors;
    }
}
