package ru.otus.kulygin.exception;

public class CommentDoesNotExistException extends RuntimeException {

    public CommentDoesNotExistException(String message) {
        super(message);
    }

    public CommentDoesNotExistException(Throwable cause) {
        super(cause);
    }

}
