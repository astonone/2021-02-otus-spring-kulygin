package ru.otus.kulygin.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.kulygin.dao.BookDao;
import ru.otus.kulygin.dao.impl.mapper.BookLazyMapper;
import ru.otus.kulygin.dao.impl.mapper.BookMapper;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.domain.Options;
import ru.otus.kulygin.exception.BookDoesNotExistException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao for working with books ")
@JdbcTest
@Import({BookDaoJdbc.class, BookMapper.class, BookLazyMapper.class})
class BookDaoJdbcTest {

    public static final int EXPECTED_BOOK_COUNT = 10;

    public static final long FOR_INSERT_BOOK_ID = 11;
    public static final String FOR_INSERT_BOOK_TITLE = "Вино из одуванчиков";
    public static final long FOR_INSERT_AUTHOR_ID = 3;
    public static final String FOR_INSERT_AUTHOR_FIRST_NAME = "Рэй";
    public static final String FOR_INSERT_AUTHOR_LAST_NAME = "Бредбери";
    public static final long FOR_INSERT_GENRE_ID = 4;
    public static final String FOR_INSERT_GENRE_NAME = "Роман";

    public static final long EXISTED_BOOK_ID = 1;
    public static final String EXISTED_BOOK_TITLE = "Черновик";
    public static final long EXISTED_AUTHOR_ID = 1;
    public static final String EXISTED_AUTHOR_FIRST_NAME = "Сергей";
    public static final String EXISTED_AUTHOR_LAST_NAME = "Лукьяненко";
    public static final long EXISTED_GENRE_ID = 2;
    public static final String EXISTED_GENRE_NAME = "Фантастика";

    public static final long NOT_EXISTED_BOOK_ID = 555;

    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("should return true for existed entity")
    void shouldReturnTrueForExistedEntity() {
        assertThat(bookDao.existsById(EXISTED_BOOK_ID)).isTrue();
    }

    @Test
    @DisplayName("should return false for existed entity")
    void shouldReturnFalseForExistedEntity() {
        assertThat(bookDao.existsById(NOT_EXISTED_BOOK_ID)).isFalse();
    }

    @Test
    @DisplayName("should return expected books count")
    void shouldReturnExpectedBooksCount() {
        int actualBooksCount = bookDao.count();
        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @Test
    @DisplayName("add book to database")
    void shouldInsertBook() {
        Book expectedBook = Book.builder()
                .id(FOR_INSERT_BOOK_ID)
                .title(FOR_INSERT_BOOK_TITLE)
                .author(Author.builder()
                        .id(FOR_INSERT_AUTHOR_ID)
                        .firstName(FOR_INSERT_AUTHOR_FIRST_NAME)
                        .lastName(FOR_INSERT_AUTHOR_LAST_NAME)
                        .build())
                .genre(Genre.builder()
                        .id(FOR_INSERT_GENRE_ID)
                        .name(FOR_INSERT_GENRE_NAME)
                        .build())
                .build();

        bookDao.insert(expectedBook);

        Book actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("not add book to database because book with this id already exists")
    void shouldNotInsertBook() {
        Book expectedBook = Book.builder()
                .id(EXISTED_BOOK_ID)
                .title(EXISTED_BOOK_TITLE)
                .author(Author.builder()
                        .id(EXISTED_AUTHOR_ID)
                        .firstName(EXISTED_AUTHOR_FIRST_NAME)
                        .lastName(EXISTED_AUTHOR_LAST_NAME)
                        .build())
                .genre(Genre.builder()
                        .id(EXISTED_GENRE_ID)
                        .name(EXISTED_GENRE_NAME)
                        .build())
                .build();

        assertThatThrownBy(() -> bookDao.insert(expectedBook))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("return expected book by id")
    void shouldReturnExpectedBookById() {
        Book expectedBook = Book.builder()
                .id(EXISTED_BOOK_ID)
                .title(EXISTED_BOOK_TITLE)
                .author(Author.builder()
                        .id(EXISTED_AUTHOR_ID)
                        .firstName(EXISTED_AUTHOR_FIRST_NAME)
                        .lastName(EXISTED_AUTHOR_LAST_NAME)
                        .build())
                .genre(Genre.builder()
                        .id(EXISTED_GENRE_ID)
                        .name(EXISTED_GENRE_NAME)
                        .build())
                .build();

        Book actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("return expected book by id by lazy type")
    void shouldReturnExpectedBookByIdPartial() {
        Book expectedBook = Book.builder()
                .id(EXISTED_BOOK_ID)
                .title(EXISTED_BOOK_TITLE)
                .build();

        Book actualBook = bookDao.getById(expectedBook.getId(), Options.builder()
                .isPartialLoading(true)
                .build());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("not return expected book by id because book does not exist")
    void shouldNotReturnExpectedBookById() {
        assertThatThrownBy(() -> bookDao.getById(NOT_EXISTED_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("remove book by id")
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDao.getById(EXISTED_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTED_BOOK_ID);

        assertThatThrownBy(() -> bookDao.getById(EXISTED_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("not remove book by id because book does not exist")
    void shouldNotCorrectDeleteBookByIdBecauseBookDoesNotExist() {
        assertThatThrownBy(() -> bookDao.deleteById(NOT_EXISTED_BOOK_ID))
                .isInstanceOf(BookDoesNotExistException.class);
    }


    @DisplayName("return list of books")
    @Test
    void shouldReturnExpectedBooksList() {
        List<Book> actualBookList = bookDao.getAll();

        assertThat(actualBookList)
                .hasSize(10)
                .extracting("id")
                .containsExactlyInAnyOrder(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        assertThat(actualBookList)
                .flatExtracting(Book::getGenre)
                .doesNotContainNull();
        assertThat(actualBookList)
                .flatExtracting(Book::getAuthor)
                .doesNotContainNull();
    }

    @DisplayName("return list of books by partial type")
    @Test
    void shouldReturnExpectedBooksListPartial() {
        List<Book> actualBookList = bookDao.getAll(Options.builder()
                .isPartialLoading(true)
                .build());

        assertThat(actualBookList)
                .hasSize(10)
                .extracting("id")
                .containsExactlyInAnyOrder(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        assertThat(actualBookList)
                .flatExtracting(Book::getGenre)
                .containsOnlyNulls();
        assertThat(actualBookList)
                .flatExtracting(Book::getAuthor)
                .containsOnlyNulls();
    }

}