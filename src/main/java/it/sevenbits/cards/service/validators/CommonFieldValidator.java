package it.sevenbits.cards.service.validators;

import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.*;
import it.sevenbits.cards.service.CampaignService;
import it.sevenbits.cards.service.DiscountService;
import it.sevenbits.cards.service.StoreService;
import it.sevenbits.cards.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommonFieldValidator {

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private DiscountService discountService;

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE
    );

    private static final String WHITESPACE_PATTERN = "\\s+";

    private static final Logger LOG = Logger.getLogger(CommonFieldValidator.class);

    public void isNotNullOrEmpty(
            final String value,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            if (value == null) {
                errors.put(field, key);
            } else if (value.isEmpty()) {
                errors.put(field, key);
            } else if (value.matches(WHITESPACE_PATTERN)) {
                errors.put(field, key);
            }
        }
    }

    public void isBoolean(
            final String value,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            if (!((value.equals("true")) || (value.equals("false")))) {
                errors.put(field, key);
            }
        }
    }

    public void isEmail(final String value, final Map<String, String> errors, final String field, final String key) {
        if (value != null && !errors.containsKey(field)) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(value);
            if (!matcher.find()) {
                errors.put(field, key);
            }
        }
    }

    public void shorterThan(
            final String value,
            final Integer maxLength,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (value != null && !errors.containsKey(field)) {
            if (value.length() > maxLength) {
                errors.put(field, key);
            }
        }
    }

    public void isUserAlreadyExist(
            final String email,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (email != null && email != "" && !errors.containsKey(field)) {
            User user = new User();
            try {
                user = userService.findByEmail(email);
            } catch (Exception e) {
                errors.put(field, key);
            }
            if(user != null) {
                if (user.getPassword() != null) {
                    LOG.info("Password !=  null!");
                    errors.put(field, key);
                }
            }
        }
    }

    public void isUserExist(
            final String email,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (email != null && email != "" && !errors.containsKey(field)) {
            User user = new User();
            try {
                user = userService.findByEmail(email);
            } catch (Exception e) {
                errors.put(field, key);
            }
            if (user == null) {
                errors.put(field, key);
            }
        }
    }

    public void isUserSelfSend(
            final String email,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String ownerEmail = authentication.getName();
            if(ownerEmail.equals(email)){
                errors.put(field, key);
            }
        }
    }

    public void isStoreMakerOfDiscount(
            final String discountKey,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            Long id = null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            try{
                id = discountService.findDiscountIdByMakerEmailAndKey(email, discountKey);
            }
            catch (Exception e){
                errors.put(field, key);
            }
            if(id == null){
                errors.put(field, key);
            }
        }
    }


    public void isDiscountPrivateByKey(
            final String discountKey,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            if (!errors.containsKey(field)) {
                Boolean isHidden = true;
                try{
                     isHidden = discountService.findDiscountHiddenStatusByKey(discountKey);
                }
                catch (Exception e){
                    errors.put(field, key);
                }
                if(isHidden){
                    errors.put(field, key);
                }
            }
        }
    }

    public void isDiscountExistByKey(
            final String discountKey,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            Boolean isHidden = null;
            try {
                isHidden = discountService.findDiscountHiddenStatusByKey(discountKey);
            } catch (Exception e) {
                errors.put(field, key);
            }
            if (isHidden == null) {
                errors.put(field, key);
            }
        }
    }
    public void isPercent(
            final String discountPercent,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            int percent;
            try {
                percent = Integer.parseInt(discountPercent);
            } catch (Exception e) {
                percent = 0;
            }
            if (percent == 0) {
                errors.put(field, key);
            }
        }
    }
    public void isAccountHashExist(
            final String hash,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            Long id = null;
            try {
                id = userService.findIdByHash(hash);
            } catch (Exception e) {
                errors.put(field, key);
            }
            if (id == null) {
                errors.put(field, key);
            }
        }
    }
    public void isDiscountHashExist(
            final String hash,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            if (!errors.containsKey(field)) {
                Long id = null;
                try {
                    id = discountService.findIdByHash(hash);
                } catch (Exception e) {
                    errors.put(field, key);
                }
                if (id == null) {
                    errors.put(field, key);
                }
            }
        }
    }
    public void isBackerPercent(
            final String discountPercent,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            int percent;
            try {
                percent = Integer.parseInt(discountPercent);
            } catch (Exception e) {
                percent = -1;
            }
            if (percent == -1 ) {
                errors.put(field, key);
            }
        }
    }
}

