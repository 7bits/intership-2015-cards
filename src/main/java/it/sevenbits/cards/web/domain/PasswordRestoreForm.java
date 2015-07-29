package it.sevenbits.cards.web.domain;

/**
 * Created by taro on 27.07.15.
 */
public class PasswordRestoreForm {
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("RegistrationForm[email=%s]", email);
    }
}
