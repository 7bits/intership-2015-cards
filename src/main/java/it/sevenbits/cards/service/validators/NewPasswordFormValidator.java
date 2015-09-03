package it.sevenbits.cards.service.validators;


import it.sevenbits.cards.web.domain.forms.NewPasswordForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class NewPasswordFormValidator {

    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(NewPasswordFormValidator.class);

    public HashMap<String, String> validate(final NewPasswordForm form) {

        LOG.info("NewPasswordFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(form.getPassword(), errors, "password", "Новый пароль не может быть пустым.");

        validator.shorterThan(form.getPassword(), 255, errors, "password", "Новый пароль не может быть больше 255 символов.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}