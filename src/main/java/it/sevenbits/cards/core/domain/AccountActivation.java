package it.sevenbits.cards.core.domain;

import java.io.Serializable;

/**
 * Created by deamor on 12.08.15.
 */
public class AccountActivation implements Serializable {
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
        return String.format("AccountActivation[email=%s, hash=%s]",
                email, hash);
    }
}
