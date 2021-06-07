package ru.otus.kulygin.exception;

public class BookDoesNotExistException extends RuntimeException {

    public BookDoesNotExistException() {
    }

    public BookDoesNotExistException(String message) {
        super(message);
    }

}
