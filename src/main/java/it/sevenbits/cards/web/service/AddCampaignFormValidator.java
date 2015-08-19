package it.sevenbits.cards.web.service;

import it.sevenbits.cards.web.domain.AddCampaignForm;
import it.sevenbits.cards.web.domain.BindForm;
import it.sevenbits.cards.web.domain.UseForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddCampaignFormValidator {
    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(AddCampaignFormValidator.class);

    public HashMap<String, String> validate(AddCampaignForm addCampaignForm){
        LOG.info("AddCampaignFormValidator started for: " + addCampaignForm.toString());
        HashMap<String, String> errors = new HashMap<>();

        validator.isNotNullOrEmpty(addCampaignForm.getName(), errors, "name", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(addCampaignForm.getDescription(), errors, "description", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(addCampaignForm.getPercent(), errors, "percent", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(addCampaignForm.getBackerPercent(), errors, "backerPercent", "Поле не должно быть пустым");

        validator.shorterThan(addCampaignForm.getName(), 255, errors, "name", "Поле должно быть короче, чем 255 символов.");
        validator.shorterThan(addCampaignForm.getDescription(), 255, errors, "description", "Поле должно быть короче, чем 255 символов.");
        validator.shorterThan(addCampaignForm.getPercent(), 2, errors, "percent", "Поле должно содержать число от 1 до 99.");
        validator.shorterThan(addCampaignForm.getBackerPercent(), 2, errors, "backerPercent", "Поле должно содержать число от 1 до 99.");

        validator.isPercent(addCampaignForm.getPercent(), errors, "percent", "Число должно быть меньше 100.");
        validator.isBackerPercent(addCampaignForm.getBackerPercent(), errors, "backerPercent", "Число должно быть меньше 100.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}