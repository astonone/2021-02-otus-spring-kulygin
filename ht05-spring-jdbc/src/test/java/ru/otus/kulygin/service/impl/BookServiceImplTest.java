package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.kulygin.dao.BookDao;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Options;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.EntityUniqIdException;
import ru.otus.kulygin.service.BookService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BookServiceImpl.class)
@DisplayName(value = "BookServiceImpl should ")
class BookServiceImplTest {

    public static final int EXPECTED_BOOK_COUNT = 5;
    public static final long NOT_EXISTED_BOOK_ID = 6;

    public static final long FOR_INSERT_BOOK_ID = 11;

    @Autowired
    private BookService bookService;

    @MockBean
    private BookDao bookDao;

    @Test
    @DisplayName("should return expected books count")
    public void shouldCountBooks() {
        when(bookDao.count()).thenReturn(EXPECTED_BOOK_COUNT);

        assertThat(bookService.count()).isEqualTo(EXPECTED_BOOK_COUNT);
        verify(bookDao).count();
    }

    @Test
    @DisplayName("add book to database")
    public void shouldInsertBook() {
        Book book = Book.builder()
                .id(FOR_INSERT_BOOK_ID)
                .build();

        bookService.insert(book);

        verify(bookDao).insert(book);
    }

    @Test
    @DisplayName("not add book to database because book with this id already exists")
    public void shouldNotInsertBook() {
        Book book = Book.builder()
                .id(FOR_INSERT_BOOK_ID)
                .build();

        doThrow(new DuplicateKeyException("")).when(bookDao).insert(book);

        assertThatThrownBy(() -> bookService.insert(book))
                .isInstanceOf(EntityUniqIdException.class);
    }

    @Test
    @DisplayName("return expected book by id")
    public void shouldGetBookById() {
        Book book = Book.builder()
                .id(FOR_INSERT_BOOK_ID)
                .build();
        final Options options = Options.builder().build();

        when(bookDao.getById(book.getId(), options)).thenReturn(book);

        final Book result = bookService.getById(book.getId());

        assertThat(result).isEqualTo(book);
        verify(bookDao).getById(book.getId(), options);
    }

    @Test
    @DisplayName("return expected book by id partial")
    public void shouldGetBookByIdPartial() {
        Book book = Book.builder()
                .id(FOR_INSERT_BOOK_ID)
                .build();
        final Options options = Options.builder()
                .isPartialLoading(true)
                .build();

        when(bookDao.getById(book.getId(), options)).thenReturn(book);

        final Book result = bookService.getById(book.getId(), options);

        assertThat(result).isEqualTo(book);
        verify(bookDao).getById(book.getId(), options);
    }

    @Test
    @DisplayName("not return expected book by id because book does not exist")
    void shouldNotReturnExpectedBookById() {
        when(bookDao.getById(NOT_EXISTED_BOOK_ID, Options.builder().build())).thenThrow(EmptyResultDataAccessException.class);

        assertThatThrownBy(() -> bookService.getById(NOT_EXISTED_BOOK_ID))
                .isInstanceOf(BookDoesNotExistException.class);
    }

    @Test
    @DisplayName("return list of books")
    public void shouldGetAllBook() {
        List<Book> bookList = Collections.singletonList(Book.builder().build());
        final Options options = Options.builder().build();
        when(bookDao.getAll(options)).thenReturn(bookList);

        final List<Book> all = bookService.getAll();
        assertThat(all).isEqualTo(bookList);
        verify(bookDao).getAll(options);
    }

    @Test
    @DisplayName("return list of books partial")
    public void shouldGetAllBookPartial() {
        List<Book> bookList = Collections.singletonList(Book.builder().build());
        final Options options = Options.builder()
                .isPartialLoading(true)
                .build();
        when(bookDao.getAll(options)).thenReturn(bookList);

        final List<Book> all = bookService.getAll(options);
        assertThat(all).isEqualTo(bookList);
        verify(bookDao).getAll(options);
    }

    @Test
    @DisplayName("remove book by id")
    void shouldCorrectDeleteBookById() {
        bookDao.deleteById(FOR_INSERT_BOOK_ID);

        verify(bookDao).deleteById(FOR_INSERT_BOOK_ID);
    }

    @Test
    @DisplayName("not remove book by id because book does not exist")
    void shouldNotCorrectDeleteBookByIdBecauseBookDoesNotExist() {
        doThrow(BookDoesNotExistException.class).when(bookDao).deleteById(NOT_EXISTED_BOOK_ID);

        assertThatThrownBy(() -> bookService.deleteById(NOT_EXISTED_BOOK_ID))
                .isInstanceOf(BookDoesNotExistException.class);
    }

}