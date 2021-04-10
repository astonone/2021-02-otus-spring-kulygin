package ru.otus.kulygin.exception;

public class BookDoesNotExistException extends RuntimeException {

    public BookDoesNotExistException(String message) {
        super(message);
    }

}
