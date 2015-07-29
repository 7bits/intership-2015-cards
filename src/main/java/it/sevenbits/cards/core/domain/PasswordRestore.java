package it.sevenbits.cards.core.domain;

import java.io.Serializable;

/**
 * Created by taro on 27.07.15.
 */
public class PasswordRestore implements Serializable {
    private String email;
    private String hash;

    public String getEmail() {
        return email;
    }

    public String getHash() {
        return hash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return String.format("PasswordRestore[email=%s, hash=%s]",
                email, hash);
    }
}
