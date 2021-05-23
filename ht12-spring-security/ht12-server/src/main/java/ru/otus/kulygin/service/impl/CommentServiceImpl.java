package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.repository.BookRepository;
import ru.otus.kulygin.repository.CommentRepository;
import ru.otus.kulygin.service.CommentService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final MappingService mappingService;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository, MappingService mappingService) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.mappingService = mappingService;
    }

    @Override
    public Optional<CommentDto> getById(String id) {
        return commentRepository.findById(id).map(comment -> mappingService.map(comment, CommentDto.class));
    }

    @Override
    public void insert(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteById(String id) {
        if (!commentRepository.existsById(id)) {
            throw new CommentDoesNotExistException("Comment does not exist");
        }
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> findAllByBookId(String bookId) {
        return mappingService.mapAsList(commentRepository.findAllByBook_Id(bookId), CommentDto.class);
    }

    @Override
    public BookDto addCommentToBook(String commentatorName, String text, String bookId) {
        val bookById = bookRepository.findById(bookId);
        if (bookById.isEmpty()) {
            throw new BookDoesNotExistException();
        }
        val comment = Comment.builder()
                .commentatorName(commentatorName)
                .text(text)
                .book(bookById.get())
                .build();
        commentRepository.save(comment);
        return bookRepository.findById(bookId).map(book -> mappingService.map(book, BookDto.class))
                .orElseThrow(() -> new BookDoesNotExistException("Book appeared!"));
    }

    @Override
    public BookDto removeCommentFromBook(String commentId, String bookId) {
        val byId = bookRepository.findById(bookId);
        if (byId.isEmpty()) {
            throw new BookDoesNotExistException();
        }
        val comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new CommentDoesNotExistException("Comment does not exist");
        }
        commentRepository.deleteById(commentId);
        return bookRepository.findById(bookId).map(book -> mappingService.map(book, BookDto.class))
                .orElseThrow(() -> new BookDoesNotExistException("Book appeared!"));
    }

}
