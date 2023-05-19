package ru.otus.kulygin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
