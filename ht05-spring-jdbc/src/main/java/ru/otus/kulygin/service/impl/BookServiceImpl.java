package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.dao.BookDao;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Options;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.EntityUniqIdException;
import ru.otus.kulygin.service.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public int count() {
        return bookDao.count();
    }

    @Override
    public void insert(Book book) {
        try {
            bookDao.insert(book);
        } catch (Exception e) {
            throw new EntityUniqIdException("Book with this id already exists: ", e);
        }
    }

    @Override
    public Book getById(long id, Options options) {
        try {
            return bookDao.getById(id, options);
        } catch (Exception e) {
            throw new BookDoesNotExistException("Book does not exist: ", e);
        }
    }

    @Override
    public List<Book> getAll(Options options) {
        return bookDao.getAll(options);
    }

    @Override
    public void deleteById(long id) {
        try {
            bookDao.deleteById(id);
        } catch (Exception e) {
            throw new BookDoesNotExistException("Book does not exist: ", e);
        }
    }

}
