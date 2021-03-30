package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    long count();

    void insert(Genre genre);

    Optional<Genre> getById(long id);

    List<Genre> getAll();

    void deleteById(long id);

}
