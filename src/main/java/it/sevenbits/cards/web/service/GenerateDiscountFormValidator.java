package it.sevenbits.cards.web.service;

import it.sevenbits.cards.web.domain.DiscountForm;
import it.sevenbits.cards.web.domain.GenerateDiscountForm;
import it.sevenbits.cards.web.service.CommonFieldValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class GenerateDiscountFormValidator {
    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(GenerateDiscountFormValidator.class);

    public HashMap<String, String> validate(final GenerateDiscountForm form) {
        /*
        private String storeName;
        private int discountPercent;
        private String userName;
        private String description;
        */
        LOG.info("GenerateDiscountFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(form.getStoreName(), errors, "storeName", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getDiscountPercent(), errors, "discountPercent", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getUserName(), errors, "userName", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getDescription(), errors, "description", "Поле не должно быть пустым");

        validator.shorterThan(form.getStoreName(), 255, errors, "storeName", "Поле должно быть короче, чем 255 символов");
        validator.shorterThan(form.getDiscountPercent(), 2, errors, "discountPercent", "Число должно быть меньше 100.");
        validator.shorterThan(form.getUserName(), 255, errors, "userName", "Поле должно быть короче, чем 255 символов");
        validator.shorterThan(form.getDescription(), 255, errors, "description", "Поле должно быть короче, чем 255 символов");

        validator.isEmail(form.getUserName(), errors, "userName", "Введите E-mail");
        validator.isPercent(form.getDiscountPercent(), errors, "discountPercent", "Поле должно содержать целочисленное значение от 1 до 99.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}