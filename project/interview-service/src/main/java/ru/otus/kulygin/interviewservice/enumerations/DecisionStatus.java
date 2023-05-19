package ru.otus.kulygin.interviewservice.enumerations;

public enum DecisionStatus {

    NOT_APPLICABLE("NOT_APPLICABLE"),
    SHOULD_BE_HIRED("SHOULD_BE_HIRED"),
    SHOULD_NOT_BE_HIRED("SHOULD_NOT_BE_HIRED");

    private final String code;

    DecisionStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
