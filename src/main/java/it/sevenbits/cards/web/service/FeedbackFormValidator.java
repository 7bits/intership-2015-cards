package it.sevenbits.cards.web.service;


import it.sevenbits.cards.web.domain.FeedbackForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FeedbackFormValidator {

    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(FeedbackFormValidator.class);

    public HashMap<String, String> validate(FeedbackForm feedbackForm) {

        LOG.info("IdFormValidator started for: " + feedbackForm.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(feedbackForm.getEmail(), errors, "email", "Поле не может быть пустым.");
        validator.isNotNullOrEmpty(feedbackForm.getTitle(), errors, "title", "Поле не может быть пустым.");
        validator.isNotNullOrEmpty(feedbackForm.getDescribe(), errors, "describe", "Поле не может быть пустым.");

        validator.shorterThan(feedbackForm.getEmail(), 255, errors, "email", "Поле не может быть пустым.");
        validator.shorterThan(feedbackForm.getTitle(), 255, errors, "title", "Поле не может быть пустым.");
        validator.shorterThan(feedbackForm.getDescribe(), 255, errors, "describe", "Поле не может быть пустым.");

        validator.isEmail(feedbackForm.getEmail(), errors, "email", "Введите корректный Email.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }
        return errors;
    }
}





