package ru.otus.kulygin.dao;

import ru.otus.kulygin.domain.Author;

import java.util.List;

public interface AuthorDao {

    boolean existsById(long id);

    int count();

    void insert(Author author);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);

}
