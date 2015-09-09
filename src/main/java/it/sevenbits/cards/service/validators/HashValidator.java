package it.sevenbits.cards.service.validators;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HashValidator {

    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(HashValidator.class);

    public HashMap<String, String> validate(String hash) {

        LOG.info("HashValidator started for: " + hash);
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(hash, errors, "hash", "Хэш не может быть пустым.");

        validator.shorterThan(hash, 255, errors, "hash", "Хэш не может быть больше 255 символов.");

        validator.isHashExist(hash, errors, "hash", "Вы не можете восстановить пароль по несуществующему хэшу.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}
