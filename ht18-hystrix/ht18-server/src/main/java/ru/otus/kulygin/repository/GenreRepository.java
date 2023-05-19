package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
