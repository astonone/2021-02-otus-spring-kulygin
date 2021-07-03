package ru.otus.kulygin.enumerations;

public enum UserAuthorities {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String authority;

    UserAuthorities(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
