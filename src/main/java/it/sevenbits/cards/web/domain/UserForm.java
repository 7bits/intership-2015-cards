package it.sevenbits.cards.web.domain;

public class UserForm {
    private Long id;
    private String email;
    private String userId;
    private String password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getIsStore() {
        return isStore;
    }

    public void setIsStore(Boolean isStore) {
        this.isStore = isStore;
    }
}
