package it.sevenbits.cards.web.domain;

/**
 * Created by taro on 20.07.15.
 */
public class RegistrationForm {
    private String email;
    private String password;
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("RegistrationForm[email=%s, password=%s, conf_password=%s]", email, password, confirmPassword);
    }
}
