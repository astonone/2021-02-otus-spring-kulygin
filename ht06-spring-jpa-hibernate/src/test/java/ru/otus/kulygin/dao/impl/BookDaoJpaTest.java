package ru.otus.kulygin.dao.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.kulygin.dao.BookDao;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao for working with books ")
@DataJpaTest
@Import({BookDaoJpa.class})
class BookDaoJpaTest {

    public static final int EXPECTED_BOOK_COUNT = 10;

    public static final String FOR_INSERT_BOOK_TITLE = "Вино из одуванчиков";
    public static final long FOR_INSERT_AUTHOR_ID = 3;
    public static final long FOR_INSERT_GENRE_ID = 4;

    public static final long EXISTED_BOOK_ID = 1;

    public static final long NOT_EXISTED_BOOK_ID = 555;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private TestEntityManager em;

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
        val actualBooksCount = bookDao.count();
        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @Test
    @DisplayName("add book to database")
    void shouldInsertBook() {
        val author = em.find(Author.class, FOR_INSERT_AUTHOR_ID);
        val genre = em.find(Genre.class, FOR_INSERT_GENRE_ID);
        val book = Book.builder()
                .title(FOR_INSERT_BOOK_TITLE)
                .author(author)
                .genre(genre)
                .build();

        bookDao.insert(book);
        assertThat(book.getId()).isNotNull();

        val actualBook = em.find(Book.class, book.getId());
        assertThat(actualBook).isNotNull()
                .matches(b -> b.getTitle().equals(FOR_INSERT_BOOK_TITLE))
                .matches(b -> b.getGenre() != null && b.getGenre().equals(genre))
                .matches(b -> b.getAuthor() != null && b.getAuthor().equals(author));
    }

    @Test
    @DisplayName("return expected book by id")
    void shouldReturnExpectedBookById() {
        val optionalActualBook = bookDao.getById(EXISTED_BOOK_ID);
        val expectedBook = em.find(Book.class, EXISTED_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("not return expected book by id because book does not exist")
    void shouldNotReturnExpectedBookById() {
        assertThat(bookDao.getById(NOT_EXISTED_BOOK_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("remove book by id")
    void shouldCorrectDeleteBookById() {
        val firstBook = em.find(Book.class, EXISTED_BOOK_ID);
        assertThat(firstBook).isNotNull();
        em.detach(firstBook);

        bookDao.deleteById(EXISTED_BOOK_ID);
        val deletedBook = em.find(Book.class, EXISTED_BOOK_ID);

        assertThat(deletedBook).isNull();
    }

    @Test
    @DisplayName("not remove book by id because book does not exist")
    void shouldNotCorrectDeleteBookByIdBecauseBookDoesNotExist() {
        assertThatThrownBy(() -> bookDao.deleteById(NOT_EXISTED_BOOK_ID))
                .isInstanceOf(Exception.class);
    }


    @DisplayName("return list of books")
    @Test
    void shouldReturnExpectedBooksList() {
        val actualBookList = bookDao.getAll();

        val expectedBookList = em.getEntityManager()
                .createQuery("select b from Book b", Book.class).getResultList();

        assertThat(actualBookList)
                .hasSize(10)
                .isEqualTo(expectedBookList);
    }

}
