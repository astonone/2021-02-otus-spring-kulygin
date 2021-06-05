package ru.otus.kulygin.enumeration;

public enum UserRoles {

    DEVELOPER("ROLE_DEVELOPER"),
    HR("ROLE_HR");

    private final String roleName;

    UserRoles(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

}
