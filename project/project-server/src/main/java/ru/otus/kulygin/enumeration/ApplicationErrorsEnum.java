package ru.otus.kulygin.enumeration;

public enum ApplicationErrorsEnum {

    INTERVIEWER_NOT_FOUND(1L, "Interviewer by id has not found"),
    RELATED_ENTITY(2L, "");

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
