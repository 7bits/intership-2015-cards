package it.sevenbits.cards.validation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//We must delete this class
public class EmailValidation {
    public static boolean checkEmailValidity(String email) {
        Pattern pattern = Pattern.compile("^[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?)*\\.[a-z]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
