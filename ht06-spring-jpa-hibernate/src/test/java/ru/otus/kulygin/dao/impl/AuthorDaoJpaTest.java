package ru.otus.kulygin.dao.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.kulygin.dao.AuthorDao;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao for working with authors ")
@DataJpaTest
@Import({AuthorDaoJpa.class})
class AuthorDaoJpaTest {

    public static final long EXPECTED_AUTHOR_COUNT = 10;

    public static final String FOR_INSERT_AUTHOR_FIRST_NAME = "Ася";
    public static final String FOR_INSERT_AUTHOR_LAST_NAME = "Казанцева";

    public static final long EXISTED_AUTHOR_ID = 1;

    public static final long EXISTED_AUTHOR_ID_WITHOUT_RELATED_BOOKS = 7;
    public static final long NOT_EXISTED_AUTHOR_ID = 999;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("should return true for existed entity")
    void shouldReturnTrueForExistedEntity() {
        assertThat(authorDao.existsById(EXISTED_AUTHOR_ID)).isTrue();
    }

    @Test
    @DisplayName("should return false for existed entity")
    void shouldReturnFalseForExistedEntity() {
        assertThat(authorDao.existsById(NOT_EXISTED_AUTHOR_ID)).isFalse();
    }

    @Test
    @DisplayName("should return expected authors count")
    void shouldReturnExpectedAuthorsCount() {
        val actualAuthorsCount = authorDao.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
    }

    @Test
    @DisplayName("add author to database")
    void shouldInsertAuthor() {
        val author = Author.builder()
                .firstName(FOR_INSERT_AUTHOR_FIRST_NAME)
                .lastName(FOR_INSERT_AUTHOR_LAST_NAME)
                .build();

        authorDao.insert(author);
        assertThat(author.getId()).isNotNull();

        val actualAuthor = em.find(Author.class, author.getId());
        assertThat(actualAuthor).isNotNull()
                .matches(a -> a.getFirstName().equals(FOR_INSERT_AUTHOR_FIRST_NAME))
                .matches(a -> a.getLastName().equals(FOR_INSERT_AUTHOR_LAST_NAME));
    }

    @Test
    @DisplayName("return expected author by id")
    void shouldReturnExpectedAuthorById() {
        val optionalActualAuthor = authorDao.getById(EXISTED_AUTHOR_ID);
        val expectedAuthor = em.find(Author.class, EXISTED_AUTHOR_ID);
        assertThat(optionalActualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("not return expected author by id because author does not exist")
    void shouldNotReturnExpectedAuthorById() {
        assertThat(authorDao.getById(NOT_EXISTED_AUTHOR_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("remove author by id")
    void shouldCorrectDeleteAuthorById() {
        val firstAuthor = em.find(Author.class, EXISTED_AUTHOR_ID_WITHOUT_RELATED_BOOKS);
        assertThat(firstAuthor).isNotNull();
        em.detach(firstAuthor);

        authorDao.deleteById(EXISTED_AUTHOR_ID_WITHOUT_RELATED_BOOKS);
        val deletedAuthor = em.find(Author.class, EXISTED_AUTHOR_ID_WITHOUT_RELATED_BOOKS);

        assertThat(deletedAuthor).isNull();
    }

    @Test
    @DisplayName("not remove author by id because author has related books")
    void shouldNotCorrectDeleteAuthorByIdBecauseAuthorHasRelatedBooks() {
        assertThatCode(() -> authorDao.getById(EXISTED_AUTHOR_ID))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> authorDao.deleteById(EXISTED_AUTHOR_ID))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("not remove author by id because author does not exist")
    void shouldNotCorrectDeleteAuthorByIdBecauseAuthorDoesNotExist() {
        assertThatThrownBy(() -> authorDao.deleteById(NOT_EXISTED_AUTHOR_ID))
                .isInstanceOf(AuthorDoesNotExistException.class);
    }

    @Test
    @DisplayName("return list of authors")
    void shouldReturnExpectedAuthorsList() {
        val actualAuthorList = authorDao.getAll();

        val expectedAuthorList = em.getEntityManager()
                .createQuery("select a from Author a", Author.class).getResultList();

        assertThat(actualAuthorList)
                .hasSize(10)
                .isEqualTo(expectedAuthorList);
    }

}