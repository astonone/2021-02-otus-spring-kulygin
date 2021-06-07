package ru.otus.kulygin.enumerations;

public enum UserAuthorities {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String roleName;

    UserAuthorities(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
