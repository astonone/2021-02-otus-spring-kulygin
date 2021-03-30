package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    long count();

    void insert(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);

    Book addCommentToBook(String commentatorName, String text, Book book);

    Book removeCommentFromBook(long commentId);

}
