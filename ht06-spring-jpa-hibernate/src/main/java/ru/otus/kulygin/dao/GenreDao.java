package ru.otus.kulygin.dao;

import ru.otus.kulygin.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    boolean existsById(long id);

    long count();

    void insert(Genre genre);

    Optional<Genre> getById(long id);

    List<Genre> getAll();

    void deleteById(long id);

}
