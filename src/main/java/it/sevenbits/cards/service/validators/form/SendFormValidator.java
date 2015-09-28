package it.sevenbits.cards.service.validators.form;

import it.sevenbits.cards.service.validators.CommonFieldValidator;
import it.sevenbits.cards.web.domain.forms.SendForm;
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

    public HashMap<String, String> validate(final SendForm sendForm) {
        LOG.info("SendFormValidator started for: " + sendForm.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(sendForm.getEmail(), errors, "email", "Поле не должно быть пустым.");
        validator.isNotNullOrEmpty(sendForm.getKey(), errors, "key", "Поле не должно быть пустым.");

        validator.shorterThan(sendForm.getEmail(), 255, errors, "email", "Поле должно быть короче, чем 255 символов.");
        validator.shorterThan(sendForm.getKey(), 8, errors, "key", "Поле должно быть короче, чем 255 символов.");

        validator.isEmail(sendForm.getEmail(), errors, "email", "В поле должен быть введён Email.");
        validator.isUserSelfSend(sendForm.getEmail(), errors, "email", "Пользователь не может отправить скидку самому себе.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}