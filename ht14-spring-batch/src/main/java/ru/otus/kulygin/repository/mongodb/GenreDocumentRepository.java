package ru.otus.kulygin.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.mongodb.GenreDocument;

public interface GenreDocumentRepository extends MongoRepository<GenreDocument, String> {

    GenreDocument findByName(String name);

    boolean existsByName(String name);

}
