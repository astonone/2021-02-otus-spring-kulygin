package ru.otus.kulygin.enumerations;

public enum UserRoles {

    USER("USER"),
    ADMIN("ADMIN");

    private final String roleName;

    UserRoles(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
