package it.sevenbits.cards.service.validators;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserExistValidator {

    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(IdValidator.class);

    public HashMap<String, String> validate(String email) {

        LOG.info("UserExistValidator started for: " + email);
        HashMap<String, String> errors = new HashMap<>();

        validator.isUserExist(email, errors, "email", "Пользователь с данным электронным адресом уже существует.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }


}
