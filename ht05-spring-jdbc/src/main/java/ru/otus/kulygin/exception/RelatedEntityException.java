package ru.otus.kulygin.exception;

public class RelatedEntityException extends RuntimeException {

    public RelatedEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}
