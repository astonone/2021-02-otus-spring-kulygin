package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    long count();

    void insert(Genre genre);

    Optional<GenreDto> getById(long id);

    List<GenreDto> getAll();

    void deleteById(long id);

}
