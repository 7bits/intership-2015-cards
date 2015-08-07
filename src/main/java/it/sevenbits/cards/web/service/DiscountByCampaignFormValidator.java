package it.sevenbits.cards.web.service;

import it.sevenbits.cards.web.domain.DiscountByCampaignForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Service
public class DiscountByCampaignFormValidator {
    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(DiscountByCampaignFormValidator.class);

    public HashMap<String, String> validate(final DiscountByCampaignForm form) {
        LOG.info("GenerateDiscountFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(form.getEmail(), errors, "email", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getName(), errors, "name", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getDescription(), errors, "description", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getPercent(), errors, "percent", "Поле не должно быть пустым");

        validator.shorterThan(form.getEmail(), 255, errors, "email", "Поле должно быть короче, чем 255 символов");
        validator.shorterThan(form.getName(), 255, errors, "name", "Поле должно быть короче, чем 255 символов");
        validator.shorterThan(form.getDescription(), 255, errors, "description", "Поле должно быть короче, чем 255 символов");
        validator.shorterThan(form.getPercent(), 2, errors, "percent", "Поле должно быть короче, чем 255 символов");

        validator.isEmail(form.getEmail(), errors, "userName", "Поле должно содержать E-mail");
        validator.isUserExist(form.getEmail(), errors, "userName", "Пользователь с данным E-mail не существует.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}

