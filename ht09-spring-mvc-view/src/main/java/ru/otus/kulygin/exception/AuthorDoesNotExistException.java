package ru.otus.kulygin.exception;

public class AuthorDoesNotExistException extends RuntimeException {

    public AuthorDoesNotExistException(String message) {
        super(message);
    }

}
