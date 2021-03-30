package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    long count();

    void insert(Author author);

    Optional<Author> getById(long id);

    List<Author> getAll();

    void deleteById(long id);

}
