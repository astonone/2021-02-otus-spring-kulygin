package ru.otus.kulygin.interviewservice.enumerations;

public enum InterviewStatus {

    PLANNED("PLANNED"),
    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED");

    private final String code;

    InterviewStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
