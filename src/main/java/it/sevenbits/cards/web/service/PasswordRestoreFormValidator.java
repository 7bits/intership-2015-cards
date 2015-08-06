package it.sevenbits.cards.web.service;

import it.sevenbits.cards.web.domain.PasswordRestoreForm;
import it.sevenbits.cards.web.service.CommonFieldValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class PasswordRestoreFormValidator {
    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(PasswordRestoreFormValidator.class);

    public HashMap<String, String> validate(final PasswordRestoreForm form) {

        LOG.info("GenerateDiscountFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(form.getEmail(), errors, "email", "Поле не должно быть пустым.");
        validator.isEmail(form.getEmail(), errors, "email", "Поле должно содержать E-mail адрес.");
        validator.isUserExist(form.getEmail(), errors, "email", "Пользователь с данным E-mail не существует.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}