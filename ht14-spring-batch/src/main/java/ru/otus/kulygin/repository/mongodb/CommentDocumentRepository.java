package ru.otus.kulygin.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.kulygin.domain.mongodb.BookDocument;
import ru.otus.kulygin.domain.mongodb.CommentDocument;

public interface CommentDocumentRepository extends MongoRepository<CommentDocument, String> {

    boolean existsByCommentatorNameAndTextAndBook(String commentatorName, String text, BookDocument book);

}
