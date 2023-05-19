package ru.otus.kulygin.dao;

import ru.otus.kulygin.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    boolean existsById(long id);

    long count();

    void insert(Author author);

    Optional<Author> getById(long id);

    List<Author> getAll();

    void deleteById(long id);

}
