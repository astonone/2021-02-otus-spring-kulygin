package ru.otus.kulygin.service;

import ru.otus.kulygin.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    long count();

    void save(GenreDto genreDto);

    Optional<GenreDto> getById(String id);

    List<GenreDto> getAll();

    void deleteById(String id);

}
