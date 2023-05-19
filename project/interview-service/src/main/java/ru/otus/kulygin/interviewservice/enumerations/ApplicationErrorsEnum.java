package ru.otus.kulygin.interviewservice.enumerations;

public enum ApplicationErrorsEnum {

    CRITERIA_NOT_FOUND(200L, "Criteria by id has not found"),
    INTERVIEW_NOT_FOUND(501L, "Interview by id has not found"),
    INTERVIEW_STATUS_EXCEPTION(700L, "New interview must have status only: PLANNED"),
    INTERVIEW_DECISION_EXCEPTION(800L, "New interview must have decision only: NOT_APPLICABLE");

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
