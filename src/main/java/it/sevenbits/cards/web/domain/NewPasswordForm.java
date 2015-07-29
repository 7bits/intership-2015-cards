package it.sevenbits.cards.web.domain;

/**
 * Created by taro on 28.07.15.
 */
public class NewPasswordForm {

    private String password;

    private String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("RegistrationForm[password=%s, hash=%s]", password, hash);
    }

}
