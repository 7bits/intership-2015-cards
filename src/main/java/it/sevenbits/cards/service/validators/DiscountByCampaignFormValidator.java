package it.sevenbits.cards.service.validators;

import it.sevenbits.cards.web.domain.forms.DiscountByCampaignForm;
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
        LOG.info("DiscountByCampaignFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(form.getEmail(), errors, "email", "Поле не должно быть пустым");

        validator.shorterThan(form.getEmail(), 255, errors, "email", "Поле должно быть короче, чем 255 символов");

        validator.isEmail(form.getEmail(), errors, "email", "Поле должно содержать E-mail");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}

