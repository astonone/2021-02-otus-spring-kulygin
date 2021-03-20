package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Genre;

import java.util.List;

public interface GenreService {

    int count();

    void insert(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    void deleteById(long id);

}
