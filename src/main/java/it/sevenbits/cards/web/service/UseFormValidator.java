package it.sevenbits.cards.web.service;

import it.sevenbits.cards.web.domain.UseForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UseFormValidator {
    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(UseFormValidator.class);

    public HashMap<String, String> validate(final UseForm useForm, String storeName){
        LOG.info("UseFormValidator started for: " + useForm.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(useForm.getKey(), errors, "key", "Поле не должно быть пустым.");

        validator.shorterThan(useForm.getKey(), 8, errors, "key", "Поле должно быть короче, чем 8 символов.");

        validator.isDiscountExist(useForm.getKey(), errors, "key", "Скидки с данным ключом не существует.");

        validator.isStoreMakerOfDiscount(useForm.getKey(), storeName, errors, "key", "Магазин должен быть создателем скидки.");

        validator.isDiscountHidden(useForm.getKey(), errors, "key", "Магазин может удалить только скидки для использования, а не для отправки.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}