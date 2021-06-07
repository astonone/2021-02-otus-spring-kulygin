package ru.otus.kulygin.exception;

public class GenreDoesNotExistException extends RuntimeException {

    public GenreDoesNotExistException() {
    }

    public GenreDoesNotExistException(String message) {
        super(message);
    }

}
