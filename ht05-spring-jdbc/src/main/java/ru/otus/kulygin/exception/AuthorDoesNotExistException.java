package ru.otus.kulygin.exception;

public class AuthorDoesNotExistException extends RuntimeException {

    public AuthorDoesNotExistException(String message) {
        super(message);
    }

    public AuthorDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public AuthorDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
