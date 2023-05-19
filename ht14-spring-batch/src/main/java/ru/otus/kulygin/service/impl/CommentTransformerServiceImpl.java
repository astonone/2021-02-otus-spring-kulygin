package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.mongodb.BookDocument;
import ru.otus.kulygin.domain.mongodb.CommentDocument;
import ru.otus.kulygin.domain.rdb.Comment;
import ru.otus.kulygin.repository.mongodb.BookDocumentRepository;
import ru.otus.kulygin.repository.mongodb.CommentDocumentRepository;
import ru.otus.kulygin.service.CommentTransformerService;

@Service
public class CommentTransformerServiceImpl implements CommentTransformerService {

    private final BookDocumentRepository bookDocumentRepository;
    private final CommentDocumentRepository commentDocumentRepository;

    public CommentTransformerServiceImpl(BookDocumentRepository bookDocumentRepository, CommentDocumentRepository commentDocumentRepository) {
        this.bookDocumentRepository = bookDocumentRepository;
        this.commentDocumentRepository = commentDocumentRepository;
    }

    @Override
    public CommentDocument transform(Comment comment) {
        final BookDocument book = bookDocumentRepository.findByTitleAndAuthor_FirstNameAndAuthor_LastName(
                comment.getBook().getTitle(),
                comment.getBook().getAuthor().getFirstName(),
                comment.getBook().getAuthor().getLastName());
        if (commentDocumentRepository.existsByCommentatorNameAndTextAndBook(comment.getCommentatorName(),
                comment.getText(), book)) {
            return null;
        }
        return CommentDocument.builder()
                .commentatorName(comment.getCommentatorName())
                .text(comment.getText())
                .book(book)
                .build();
    }

}
