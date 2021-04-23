package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.kulygin.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
