package it.sevenbits.cards.service.validators;

import it.sevenbits.cards.core.repository.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommonFieldValidator {

    @Autowired
    @Qualifier(value = "userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier(value = "discountPersistRepository")
    private DiscountRepository discountRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DiscountHashRepository discountHashRepository;

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
            final String value,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (value != null && !value.equals("")) {
            String userName;
            try {
                userName = userRepository.findByUsername(value).getUsername();
            } catch (Exception e) {
                userName = "";
            }
            if (!userName.equals("")) {
                errors.put(field, key);
            }
        }
    }

    public void isUserExist(
            final String value,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field) && value != null) {
            String userName;
            try {
                userName = userRepository.findByUsername(value).getUsername();
            } catch (Exception e) {
                userName = "";
            }
            if (userName.equals("")) {
                errors.put(field, key);
            }
        }
    }

    public void isUserSelfSend(
            final String email,
            final String uin,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            String userId = "";
            try {
                try {
                    userId = discountRepository.findDiscountOwner(uin);
                } catch (Exception e) {
                    userId = "";
                }
                String userIdByEmail = userRepository.findUserIdByUserName(email);
                if (userId.equals(userIdByEmail)) {
                    errors.put(field, key);
                }
            } catch (Exception e) {

            }
        }
    }

    public void isStoreMakerOfDiscount(
            final String discountKey,
            final String storeName,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            if (!discountKey.equals("")) {
                String discountMaker;
                try {
                    discountMaker = discountRepository.findDiscountMaker(discountKey);
                } catch (Exception e) {
                    discountMaker = "";
                }
                if (discountMaker == null) {
                    errors.put(field, key);
                } else if (!discountMaker.equals(storeName)) {
                    errors.put(field, key);
                }
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
            if (!discountKey.equals("")) {
                Boolean isHidden;
                try {
                    isHidden = discountRepository.findHiddenStatusByKey(discountKey);
                } catch (Exception e) {
                    isHidden = true;
                }
                if (isHidden == null) {
                    errors.put(field, key);
                } else if (isHidden) {
                    errors.put(field, key);
                }
            }
        }
    }

    public void isDiscountPublicByUin(
            final String discountUin,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            if (!discountUin.equals("")) {
                Boolean isHidden;
                try {
                    isHidden = discountRepository.findHiddenStatusByUin(discountUin);
                } catch (Exception e) {
                    isHidden = false;
                }
                if (isHidden == null) {
                    errors.put(field, key);
                } else if (!isHidden) {
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
            Long discountId;
            try {
                discountId = discountRepository.findDiscountIdByKey(discountKey);
            } catch (Exception e) {
                discountId = null;
            }
            if (discountId == null) {
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
    public void isHashExist(
            final String hash,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            String email;
            try {
                email = restorePasswordRepository.findEmailByHash(hash);
            } catch (Exception e) {
                email = null;
            }
            if (email == null) {
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
            String email;
            try {
                email = accountRepository.findEmailByHash(hash);
            } catch (Exception e) {
                email = null;
            }
            if (email == null) {
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
            Long id;
            try {
                id = discountHashRepository.findIdByHash(hash);
            } catch (Exception e) {
                id = null;
            }
            if (id == null) {
                errors.put(field, key);
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
