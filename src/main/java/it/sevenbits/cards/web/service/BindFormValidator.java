package it.sevenbits.cards.web.service;

import it.sevenbits.cards.web.domain.BindForm;
import it.sevenbits.cards.web.domain.UseForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BindFormValidator {
    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(BindFormValidator.class);

    public HashMap<String, String> validate(final BindForm bindForm, String userName){
        LOG.info("BindFormValidator started for: " + bindForm.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(bindForm.getUin(), errors, "uin", "Поле не должно быть пустым.");

        validator.shorterThan(bindForm.getUin(), 8, errors, "uin", "Поле должно быть короче, чем 8 символов.");

        validator.isDiscountPublicByUin(bindForm.getUin(), errors, "uin", "Скидки с данным ключом не существует.");

        validator.isDiscountPublicByUin(bindForm.getUin(), errors, "uin", "Скидка должна находиться в секции 'Скидки для отправки'.");

        validator.isUserSelfSend(userName, bindForm.getUin(), errors, "uin", "Пользователь не может добавить скидку самому себе.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}