package ru.otus.kulygin.enumeration;

public enum ApplicationErrorsEnum {

    INTERVIEWER_NOT_FOUND(1L, "Interviewer by id has not found"),
    CRITERIA_NOT_FOUND(2L, "Criteria by id has not found"),
    CANDIDATE_NOT_FOUND(3L, "Candidate by id has not found"),
    FILE_WRITING_ERROR(3L, "Error via file writing"),
    WRONG_FILE_FORMAT_EXCEPTION(4L, "Incorrect file format! Should be only PDF"),
    INTERVIEW_TEMPLATE_NOT_FOUND(5L, "Interview template by id has not found"),
    INTERVIEW_NOT_FOUND(5L, "Interview by id has not found"),
    RELATED_ENTITY(6L, ""),
    INTERVIEW_STATUS_EXCEPTION(7L, "New interview must have status only: PLANNED"),
    INTERVIEW_DECISION_EXCEPTION(8L, "New interview must have decision only: NOT_APPLICABLE");

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
