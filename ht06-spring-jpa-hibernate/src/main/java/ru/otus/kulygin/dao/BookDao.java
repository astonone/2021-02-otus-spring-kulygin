package ru.otus.kulygin.dao;

import ru.otus.kulygin.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    boolean existsById(long id);

    long count();

    void insert(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);

}
