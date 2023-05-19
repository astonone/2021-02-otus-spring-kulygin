package ru.otus.kulygin.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.mongodb.AuthorDocument;

public interface AuthorDocumentRepository extends MongoRepository<AuthorDocument, String> {

    AuthorDocument findByFirstNameAndLastName(String firstname, String lastname);

    boolean existsByFirstNameAndLastName(String firstname, String lastname);

}
