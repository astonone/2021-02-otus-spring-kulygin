package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.repository.BookRepository;
import ru.otus.kulygin.repository.CommentRepository;
import ru.otus.kulygin.service.BookService;

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
    @Transactional(readOnly = true)
    public long count() {
        return bookRepository.count();
    }

    @Override
    @Transactional
    public void insert(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> getById(long id) {
        return bookRepository.findById(id).map(book -> mappingService.map(book, BookDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return mappingService.mapAsList(bookRepository.findAll(), BookDto.class);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookDoesNotExistException("Book does not exist");
        }
        bookRepository.deleteById(id);

    }

    @Override
    @Transactional
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
    @Transactional
    public BookDto removeCommentFromBook(long commentId) {
        val comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new CommentDoesNotExistException("Comment does not exist");
        }
        val bookId = comment.get().getBook().getId();
        commentRepository.deleteById(commentId);

        return getById(bookId).orElseThrow(() -> new BookDoesNotExistException("Book appeared!"));
    }

}
