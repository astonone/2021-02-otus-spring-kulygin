package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.repository.BookRepository;
import ru.otus.kulygin.repository.CommentRepository;
import ru.otus.kulygin.service.BookService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final MappingService mappingService;

    public BookServiceImpl(BookRepository bookRepository, CommentRepository commentRepository, MappingService mappingService) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.mappingService = mappingService;
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public void insert(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Optional<BookDto> getById(String id) {
        return bookRepository.findById(id).map(book -> mappingService.map(book, BookDto.class));
    }

    @Override
    public List<BookDto> getAll() {
        return mappingService.mapAsList(bookRepository.findAll(), BookDto.class);
    }

    @Override
    public void deleteById(String id) {
        if (!bookRepository.existsById(id)) {
            throw new BookDoesNotExistException("Book does not exist");
        }
        bookRepository.deleteById(id);

    }

    @Override
    public BookDto addCommentToBook(String commentatorName, String text, Book book) {
        val comment = Comment.builder()
                .commentatorName(commentatorName)
                .text(text)
                .book(book)
                .build();
        commentRepository.save(comment);
        return getById(book.getId()).orElseThrow(() -> new BookDoesNotExistException("Book appeared!"));
    }

    @Override
    public BookDto removeCommentFromBook(String commentId) {
        val comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new CommentDoesNotExistException("Comment does not exist");
        }
        val bookId = comment.get().getBook().getId();
        commentRepository.deleteById(commentId);

        return getById(bookId).orElseThrow(() -> new BookDoesNotExistException("Book appeared!"));
    }

}
