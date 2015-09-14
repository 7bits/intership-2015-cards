package it.sevenbits.cards.service.validators;

import it.sevenbits.cards.web.domain.forms.KeyForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KeyFormValidator {
    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(KeyFormValidator.class);

    public HashMap<String, String> validate(final KeyForm keyForm){

        LOG.info("KeyFormValidator started for: " + keyForm.toString());

        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(keyForm.getKey(), errors, "key", "Поле не должно быть пустым.");

        validator.shorterThan(keyForm.getKey(), 8, errors, "key", "Поле должно быть короче, чем 8 символов.");

        validator.isDiscountExistByKey(keyForm.getKey(), errors, "key", "Скидки с данным ключом не существует.");

        validator.isStoreMakerOfDiscount(keyForm.getKey(), storeName, errors, "key", "Вы не являетесь создателем скидки.");

        validator.isDiscountPrivateByKey(keyForm.getKey(), errors, "key", "Скидка должна быть для использования.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}