package ru.otus.kulygin.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.val;
import org.springframework.security.access.annotation.Secured;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ru.otus.kulygin.service.impl.AuthorServiceImpl.N_A;

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

    @HystrixCommand(fallbackMethod = "buildFallbackComment")
    @Override
    public Optional<CommentDto> getById(String id) {
        return commentRepository.findById(id).map(comment -> mappingService.map(comment, CommentDto.class));
    }

    public Optional<CommentDto> buildFallbackComment(String id) {
        return Optional.of(CommentDto.builder()
                .id(N_A)
                .build());
    }

    @HystrixCommand(fallbackMethod = "buildFallbackCreateComment")
    @Override
    public void insert(Comment comment) {
        commentRepository.save(comment);
    }

    public void buildFallbackCreateComment(Comment comment) {
        // nothing
    }

    @HystrixCommand(fallbackMethod = "buildFallbackDeleteComment")
    @Override
    public void deleteById(String id) {
        if (!commentRepository.existsById(id)) {
            throw new CommentDoesNotExistException("Comment does not exist");
        }
        commentRepository.deleteById(id);
    }

    public void buildFallbackDeleteComment(String id) {
        // nothing
    }

    @HystrixCommand(fallbackMethod = "buildFallbackComments")
    @Override
    public List<CommentDto> findAllByBookId(String bookId) {
        return mappingService.mapAsList(commentRepository.findAllByBook_Id(bookId), CommentDto.class);
    }

    public List<CommentDto> buildFallbackComments(String bookId) {
        return Collections.singletonList(CommentDto.builder()
                .id(N_A)
                .build());
    }

    @Override
    @HystrixCommand(fallbackMethod = "buildFallbackAddComment")
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

    public BookDto buildFallbackAddComment(String commentatorName, String text, String bookId) {
        return BookDto.builder()
                .id(N_A)
                .build();
    }

    @Secured("ROLE_ADMIN")
    @HystrixCommand(fallbackMethod = "buildFallbackRemoveComment")
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

    public BookDto buildFallbackRemoveComment(String commentId, String bookId) {
        return BookDto.builder()
                .id(N_A)
                .build();
    }

}
