package ru.otus.kulygin.candidateservice.enumerations;

public enum ApplicationErrorsEnum {

    CANDIDATE_NOT_FOUND(300L, "Candidate by id has not found"),
    FILE_WRITING_ERROR(301L, "Error via file writing"),
    WRONG_FILE_FORMAT_EXCEPTION(400L, "Incorrect file format! Should be only PDF"),
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
