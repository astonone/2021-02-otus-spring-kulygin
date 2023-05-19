package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.kulygin.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
