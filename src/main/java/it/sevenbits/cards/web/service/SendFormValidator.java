package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.web.domain.SendForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SendFormValidator {
    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(SendFormValidator.class);

    public HashMap<String, String> validate(final SendForm form) {
        LOG.info("SendFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(form.getEmail(), errors, "email", "Поле не должно быть пустым.");
        validator.isNotNullOrEmpty(form.getUin(), errors, "uin", "Поле не должно быть пустым.");

        validator.shorterThan(form.getEmail(), 255, errors, "email", "Поле должно быть короче, чем 255 символов.");
        validator.shorterThan(form.getUin(), 8, errors, "uin", "Поле должно быть короче, чем 255 символов.");

        validator.isEmail(form.getEmail(), errors, "email", "В поле должен быть введён Email.");
        validator.isUserExist(form.getEmail(), errors, "email", "Пользователь должен существовать в системе.");
        validator.isUserSelfSend(form.getEmail(), form.getUin(), errors, "email", "Пользователь не может отправить скидку самому себе.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}