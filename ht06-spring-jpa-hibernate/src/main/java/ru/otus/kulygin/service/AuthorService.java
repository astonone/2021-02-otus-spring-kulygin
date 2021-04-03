package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    long count();

    void insert(Author author);

    Optional<AuthorDto> getById(long id);

    List<AuthorDto> getAll();

    void deleteById(long id);

}
