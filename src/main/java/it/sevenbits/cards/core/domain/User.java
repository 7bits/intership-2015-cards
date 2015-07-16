package it.sevenbits.cards.core.domain;

public class User {
    private Long id;
    private String email;
    private String userId;
    private String passwordHash;
    private Boolean isStore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Boolean getIsStore() {
        return isStore;
    }

    public void setIsStore(Boolean isStore) {
        this.isStore = isStore;
    }

    @Override
    public String toString() {
        return String.format("Discount[id=%d, email=%s, user_id=%s, password=%s, isStore=%b]",
                id, email, userId, passwordHash, isStore);
    }
}
