package ru.otus.kulygin.service.impl;

import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.kulygin.dao.BookDao;
import ru.otus.kulygin.dao.CommentDao;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final CommentDao commentDao;

    public BookServiceImpl(BookDao bookDao, CommentDao commentDao) {
        this.bookDao = bookDao;
        this.commentDao = commentDao;
    }

    @Override
    public long count() {
        return bookDao.count();
    }

    @Override
    public void insert(Book book) {
        bookDao.insert(book);
    }

    @Override

    public Optional<Book> getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
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
    public Book addCommentToBook(String commentatorName, String text, Book book) {
        val comment = Comment.builder()
                .commentatorName(commentatorName)
                .text(text)
                .book(book)
                .build();
        commentDao.insert(comment);
        return getById(book.getId()).orElseThrow(() -> new BookDoesNotExistException("Book appeared!"));
    }

    @Override
    public Book removeCommentFromBook(long commentId) {
        val comment = commentDao.getById(commentId);
        if (comment.isEmpty()) {
            throw new CommentDoesNotExistException("Comment does not exist");
        }
        val bookId = comment.get().getBook().getId();
        commentDao.deleteById(commentId);

        return getById(bookId).orElseThrow(() -> new BookDoesNotExistException("Book appeared!"));
    }

}
