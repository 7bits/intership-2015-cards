package it.sevenbits.cards.web.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IdFormValidator {

    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(IdFormValidator.class);

    public HashMap<String, String> validate(String id) {

        LOG.info("IdFormValidator started for: " + id);
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(id, errors, "id", "Id не может быть пустым.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}



