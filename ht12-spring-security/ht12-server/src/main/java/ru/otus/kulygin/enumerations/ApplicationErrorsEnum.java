package ru.otus.kulygin.enumerations;

public enum ApplicationErrorsEnum {

    GENRE_NOT_FOUND(1L, "Genre by id has not found"),
    AUTHOR_NOT_FOUND(2L, "Author by id has not found"),
    BOOK_NOT_FOUND(3L, "Book by id has not found"),
    COMMENT_NOT_FOUND(4L, "Comment by id has not found"),
    USER_NOT_FOUND(5L, "User by id has not found"),
    USER_EXIST(6L, "User with this username already exists"),
    RELATED_ENTITY(7L, "");

    private final Long code;
    private final String message;

    ApplicationErrorsEnum(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
