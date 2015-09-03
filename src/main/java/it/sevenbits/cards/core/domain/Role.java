package it.sevenbits.cards.core.domain;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN (0, "Admin"),
    ROLE_USER (2, "User"),
    ROLE_STORE(1,"Store"),
    ROLE_ANONIMOUS (3, "Anonymous");

    private Integer order;
    private String description;

    private Role() {
        this(null, null);
    }

    private Role(Integer order, String description) {
        this.order = order;
        this.description = description;
    }

    public static Role createRole(final String role) {
        return Role.valueOf(role);
    }

    @Override
    public String getAuthority() {
        return this.name();
    }

    public String getName() {
        return this.name();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
