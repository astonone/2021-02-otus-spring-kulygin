package ru.otus.kulygin.service;

import ru.otus.kulygin.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    long count();

    BookDto save(BookDto bookDto);

    Optional<BookDto> getById(String id);

    List<BookDto> getAll();

    void deleteById(String id);

}
