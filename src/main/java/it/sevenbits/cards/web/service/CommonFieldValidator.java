package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.core.repository.UserRepository;
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
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
            "^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?)*\\.[a-z]+$", Pattern.CASE_INSENSITIVE
    );
    private static final String WHITESPACE_PATTERN = "\\s+";

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
            if (!(value.equals("true"))||(value.equals("false"))) {
                errors.put(field, key);
            }
        }
    }

    public void isEmail(final String value, final Map<String, String> errors, final String field, final String key) {
        if (value != null && !errors.containsKey(field)) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(value);
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
    ){
        if(!value.equals("")){
            String userName;
            try {
                userName = userRepository.findByUsername(value).getUsername();
            }
            catch (Exception e){
                userName="";
            }
            if(!userName.equals("")){
                errors.put(field, key);
            }
        }
    }
    public void isUserExist(
            final String value,
            final Map<String, String> errors,
            final String field,
            final String key
    ){
        if (!errors.containsKey(field)) {
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
}