package ru.otus.kulygin.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.kulygin.dao.GenreDao;
import ru.otus.kulygin.dao.impl.mapper.GenreMapper;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.exception.GenreDoesNotExistException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao for working with Genres ")
@JdbcTest
@Import({GenreDaoJdbc.class, GenreMapper.class})
class GenreDaoJdbcTest {

    public static final int EXPECTED_GENRE_COUNT = 10;

    public static final long FOR_INSERT_GENRE_ID = 11;
    public static final String FOR_INSERT_GENRE_NAME = "Рассказ";

    public static final long EXISTED_GENRE_ID = 1;
    public static final String EXISTED_GENRE_NAME = "Драма";

    public static final long EXISTED_GENRE_ID_WITHOUT_RELATED_BOOKS = 8;
    public static final long NOT_EXISTED_GENRE_ID = 555;

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("should return true for existed entity")
    void shouldReturnTrueForExistedEntity() {
        assertThat(genreDao.existsById(EXISTED_GENRE_ID)).isTrue();
    }

    @Test
    @DisplayName("should return false for existed entity")
    void shouldReturnFalseForExistedEntity() {
        assertThat(genreDao.existsById(NOT_EXISTED_GENRE_ID)).isFalse();
    }

    @Test
    @DisplayName("should return expected genres count")
    void shouldReturnExpectedGenresCount() {
        int actualGenresCount = genreDao.count();
        assertThat(actualGenresCount).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @Test
    @DisplayName("add genre to database")
    void shouldInsertGenre() {
        Genre expectedGenre = Genre.builder()
                .id(FOR_INSERT_GENRE_ID)
                .name(FOR_INSERT_GENRE_NAME)
                .build();

        genreDao.insert(expectedGenre);

        Genre actualGenre = genreDao.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("not add genre to database because genre with this id already exists")
    void shouldNotInsertGenre() {
        Genre expectedGenre = Genre.builder()
                .id(EXISTED_GENRE_ID)
                .name(EXISTED_GENRE_NAME)
                .build();

        assertThatThrownBy(() -> genreDao.insert(expectedGenre))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("return expected genre by id")
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = Genre.builder()
                .id(EXISTED_GENRE_ID)
                .name(EXISTED_GENRE_NAME)
                .build();

        Genre actualGenre = genreDao.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("not return expected genre by id because genre does not exist")
    void shouldNotReturnExpectedGenreById() {
        assertThatThrownBy(() -> genreDao.getById(NOT_EXISTED_GENRE_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("remove genre by id")
    void shouldCorrectDeleteGenreById() {
        assertThatCode(() -> genreDao.getById(EXISTED_GENRE_ID_WITHOUT_RELATED_BOOKS))
                .doesNotThrowAnyException();

        genreDao.deleteById(EXISTED_GENRE_ID_WITHOUT_RELATED_BOOKS);

        assertThatThrownBy(() -> genreDao.getById(EXISTED_GENRE_ID_WITHOUT_RELATED_BOOKS))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("not remove genre by id because genre has related books")
    void shouldNotCorrectDeleteGenreByIdBecauseGenreHasRelatedBooks() {
        assertThatCode(() -> genreDao.getById(EXISTED_GENRE_ID))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> genreDao.deleteById(EXISTED_GENRE_ID))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("not remove genre by id because genre does not exist")
    void shouldNotCorrectDeleteGenreByIdBecauseGenreDoesNotExist() {
        assertThatThrownBy(() -> genreDao.deleteById(NOT_EXISTED_GENRE_ID))
                .isInstanceOf(GenreDoesNotExistException.class);
    }

    @DisplayName("return list of genres")
    @Test
    void shouldReturnExpectedGenresList() {
        List<Genre> actualGenreList = genreDao.getAll();

        assertThat(actualGenreList)
                .hasSize(10)
                .extracting("id")
                .containsExactlyInAnyOrder(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
    }

}