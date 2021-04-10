package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    long count();

    void insert(Book book);

    Optional<BookDto> getById(String id);

    List<BookDto> getAll();

    void deleteById(String id);

    BookDto addCommentToBook(String commentatorName, String text, Book book);

    BookDto removeCommentFromBook(String commentId);

}
