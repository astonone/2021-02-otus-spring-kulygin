package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Author;

import java.util.List;

public interface AuthorService {

    int count();

    void insert(Author author);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);

}
