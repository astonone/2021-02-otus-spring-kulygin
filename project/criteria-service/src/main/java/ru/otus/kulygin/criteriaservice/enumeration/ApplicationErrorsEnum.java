package ru.otus.kulygin.criteriaservice.enumeration;

public enum ApplicationErrorsEnum {

    CRITERIA_NOT_FOUND(200L, "Criteria by id has not found"),
    RELATED_ENTITY(600L, ""),
    INVALID_FILE_FOR_BATCH(777L, "Problem with storaging file for batch"),
    JOB_DOES_NOT_EXIST(778L, "Job for import criterias was not found"),
    JOB_INSTANCE_ALREADY_EXISTS(779L, "Job instance already exists"),
    JOB_PARAMETERS_INVALID(780L, "Invalid job parameters"),
    JOB_INSTANCE_DOES_NOT_EXIST(781L, "Job instance was not found");

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
