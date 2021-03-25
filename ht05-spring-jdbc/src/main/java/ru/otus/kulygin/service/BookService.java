package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Options;

import java.util.List;

public interface BookService {

    int count();

    void insert(Book book);

    default Book getById(long id) {
        return getById(id, Options.builder().build());
    }

    Book getById(long id, Options options);

    default List<Book> getAll() {
        return getAll(Options.builder().build());
    }

    List<Book> getAll(Options options);

    void deleteById(long id);

}
