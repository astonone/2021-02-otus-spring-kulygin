package ru.otus.kulygin.service;

import ru.otus.kulygin.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    long count();

    AuthorDto save(AuthorDto authorDto);

    Optional<AuthorDto> getById(String id);

    List<AuthorDto> getAll();

    void deleteById(String id);

}
