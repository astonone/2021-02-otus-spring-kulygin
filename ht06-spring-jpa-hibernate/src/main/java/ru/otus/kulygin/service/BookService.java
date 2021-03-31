package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    long count();

    void insert(Book book);

    Optional<BookDto> getById(long id);

    List<BookDto> getAll();

    void deleteById(long id);

    BookDto addCommentToBook(String commentatorName, String text, Book book);

    BookDto removeCommentFromBook(long commentId);

}
