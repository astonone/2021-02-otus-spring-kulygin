package ru.otus.kulygin.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.kulygin.dao.AuthorDao;
import ru.otus.kulygin.dao.impl.mapper.AuthorMapper;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao for working with authors ")
@JdbcTest
@Import({AuthorDaoJdbc.class, AuthorMapper.class})
class AuthorDaoJdbcTest {

    public static final int EXPECTED_AUTHOR_COUNT = 10;

    public static final long FOR_INSERT_AUTHOR_ID = 11;
    public static final String FOR_INSERT_AUTHOR_FIRST_NAME = "Ася";
    public static final String FOR_INSERT_AUTHOR_LAST_NAME = "Казанцева";

    public static final long EXISTED_AUTHOR_ID = 1;
    public static final String EXISTED_AUTHOR_FIRST_NAME = "Сергей";
    public static final String EXISTED_AUTHOR_LAST_NAME = "Лукьяненко";

    public static final long EXISTED_AUTHOR_ID_WITHOUT_RELATED_BOOKS = 7;
    public static final long NOT_EXISTED_AUTHOR_ID = 999;

    @Autowired
    private AuthorDao authorDao;

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
        int actualAuthorsCount = authorDao.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
    }

    @Test
    @DisplayName("add author to database")
    void shouldInsertAuthor() {
        Author expectedAuthor = Author.builder()
                .id(FOR_INSERT_AUTHOR_ID)
                .firstName(FOR_INSERT_AUTHOR_FIRST_NAME)
                .lastName(FOR_INSERT_AUTHOR_LAST_NAME)
                .build();

        authorDao.insert(expectedAuthor);

        Author actualAuthor = authorDao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("not add author to database because author with this id already exists")
    void shouldNotInsertAuthor() {
        Author expectedAuthor = Author.builder()
                .id(EXISTED_AUTHOR_ID)
                .firstName(EXISTED_AUTHOR_FIRST_NAME)
                .lastName(EXISTED_AUTHOR_LAST_NAME)
                .build();

        assertThatThrownBy(() -> authorDao.insert(expectedAuthor))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("return expected author by id")
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = Author.builder()
                .id(EXISTED_AUTHOR_ID)
                .firstName(EXISTED_AUTHOR_FIRST_NAME)
                .lastName(EXISTED_AUTHOR_LAST_NAME)
                .build();

        Author actualAuthor = authorDao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("not return expected author by id because author does not exist")
    void shouldNotReturnExpectedAuthorById() {
        assertThatThrownBy(() -> authorDao.getById(NOT_EXISTED_AUTHOR_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("remove author by id")
    void shouldCorrectDeleteAuthorById() {
        assertThatCode(() -> authorDao.getById(EXISTED_AUTHOR_ID_WITHOUT_RELATED_BOOKS))
                .doesNotThrowAnyException();

        authorDao.deleteById(EXISTED_AUTHOR_ID_WITHOUT_RELATED_BOOKS);

        assertThatThrownBy(() -> authorDao.getById(EXISTED_AUTHOR_ID_WITHOUT_RELATED_BOOKS))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("not remove author by id because author has related books")
    void shouldNotCorrectDeleteAuthorByIdBecauseAuthorHasRelatedBooks() {
        assertThatCode(() -> authorDao.getById(EXISTED_AUTHOR_ID))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> authorDao.deleteById(EXISTED_AUTHOR_ID))
                .isInstanceOf(DataIntegrityViolationException.class);
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
        List<Author> actualPersonList = authorDao.getAll();

        assertThat(actualPersonList)
                .hasSize(10)
                .extracting("id")
                .containsExactlyInAnyOrder(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
    }

}