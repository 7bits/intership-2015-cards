package it.sevenbits.cards.web.service;

import it.sevenbits.cards.web.domain.DiscountForm;
import it.sevenbits.cards.web.service.CommonFieldValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class DiscountFormValidator {
    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(DiscountFormValidator.class);

    public HashMap<String, String> validate(final DiscountForm form) {
        LOG.info("DiscountFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(form.getKey(), errors, "key", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getUin(), errors, "uin", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getIsHidden(), errors, "isHidden", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getUserId(), errors, "userId", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getStoreName(), errors, "storeName", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getDescription(), errors, "description", "Поле не должно быть пустым");

        validator.shorterThan(form.getKey(), 8, errors, "key", "Поле должно быть короче, чем 8 символов");
        validator.shorterThan(form.getUin(), 8, errors, "uin", "Поле должно быть короче, чем 8 символов");
        validator.shorterThan(form.getUserId(), 8, errors, "userId", "Поле должно быть короче, чем 8 символов");
        validator.shorterThan(form.getStoreName(), 255, errors, "storeName", "Поле должно быть короче, чем 255 символов");
        validator.shorterThan(form.getDescription(), 255, errors, "description", "Поле должно быть короче, чем 255 символов");

        validator.isBoolean(form.getIsHidden(), errors, "isHidden", "Поле должно содержать булевое значение");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}