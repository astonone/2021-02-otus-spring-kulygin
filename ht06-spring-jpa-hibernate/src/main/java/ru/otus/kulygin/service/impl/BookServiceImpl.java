package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.kulygin.dao.BookDao;
import ru.otus.kulygin.dao.CommentDao;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final CommentDao commentDao;
    private final MappingService mappingService;

    public BookServiceImpl(BookDao bookDao, CommentDao commentDao, MappingService mappingService) {
        this.bookDao = bookDao;
        this.commentDao = commentDao;
        this.mappingService = mappingService;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return bookDao.count();
    }

    @Override
    @Transactional
    public void insert(Book book) {
        bookDao.insert(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> getById(long id) {
        return bookDao.getById(id).map(book -> mappingService.map(book, BookDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return mappingService.mapAsList(bookDao.getAll(), BookDto.class);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        try {
            bookDao.deleteById(id);
        } catch (Exception e) {
            throw new BookDoesNotExistException("Book does not exist: ", e);
        }
    }

    @Override
    @Transactional
    public BookDto addCommentToBook(String commentatorName, String text, Book book) {
        val comment = Comment.builder()
                .commentatorName(commentatorName)
                .text(text)
                .book(book)
                .build();
        commentDao.insert(comment);
        return getById(book.getId()).orElseThrow(() -> new BookDoesNotExistException("Book appeared!"));
    }

    @Override
    @Transactional
    public BookDto removeCommentFromBook(long commentId) {
        val comment = commentDao.getById(commentId);
        if (comment.isEmpty()) {
            throw new CommentDoesNotExistException("Comment does not exist");
        }
        val bookId = comment.get().getBook().getId();
        commentDao.deleteById(commentId);

        return getById(bookId).orElseThrow(() -> new BookDoesNotExistException("Book appeared!"));
    }

}
