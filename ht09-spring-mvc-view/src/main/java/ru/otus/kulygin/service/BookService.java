package ru.otus.kulygin.service;

import ru.otus.kulygin.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    long count();

    void save(BookDto bookDto);

    Optional<BookDto> getById(String id);

    List<BookDto> getAll();

    void deleteById(String id);

    BookDto addCommentToBook(String commentatorName, String text, String bookId);

    BookDto removeCommentFromBook(String commentId);

}
