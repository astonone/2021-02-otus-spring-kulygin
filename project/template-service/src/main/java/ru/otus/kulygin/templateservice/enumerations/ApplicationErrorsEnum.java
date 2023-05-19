package ru.otus.kulygin.templateservice.enumerations;

public enum ApplicationErrorsEnum {

    CRITERIA_NOT_FOUND(200L, "Criteria by id has not found"),
    INTERVIEW_TEMPLATE_NOT_FOUND(500L, "Interview template by id has not found"),
    RELATED_ENTITY(600L, "");

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
