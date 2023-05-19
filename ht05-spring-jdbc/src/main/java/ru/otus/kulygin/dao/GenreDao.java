package ru.otus.kulygin.dao;

import ru.otus.kulygin.domain.Genre;

import java.util.List;

public interface GenreDao {

    boolean existsById(long id);

    int count();

    void insert(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    void deleteById(long id);

}
