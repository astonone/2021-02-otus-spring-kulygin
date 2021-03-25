package ru.otus.kulygin.exception;

public class GenreDoesNotExistException extends RuntimeException {

    public GenreDoesNotExistException(String message) {
        super(message);
    }

    public GenreDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public GenreDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
