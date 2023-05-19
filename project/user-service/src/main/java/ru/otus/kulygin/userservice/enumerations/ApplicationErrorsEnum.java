package ru.otus.kulygin.userservice.enumerations;

public enum ApplicationErrorsEnum {

    INTERVIEWER_NOT_FOUND(100L, "Interviewer by id has not found"),
    USER_EXIST(102L, "User with this username already exists"),
    RELATED_ENTITY(600L, ""),
    SECRET_KEY_ERROR(110L, "Incorrect secret key");

    private final Long code;
    private final String message;

    ApplicationErrorsEnum(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
