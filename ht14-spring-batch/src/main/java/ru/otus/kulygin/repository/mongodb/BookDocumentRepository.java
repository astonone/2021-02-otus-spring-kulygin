package ru.otus.kulygin.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.mongodb.BookDocument;

public interface BookDocumentRepository extends MongoRepository<BookDocument, String> {

    BookDocument findByTitleAndAuthor_FirstNameAndAuthor_LastName(String title, String firstname, String lastname);

    boolean existsByTitleAndAuthor_FirstNameAndAuthor_LastName(String title, String firstname, String lastname);

}
