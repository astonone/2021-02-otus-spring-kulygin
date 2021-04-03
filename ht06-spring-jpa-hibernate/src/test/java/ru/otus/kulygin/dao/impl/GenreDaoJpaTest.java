package ru.otus.kulygin.dao.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.kulygin.dao.GenreDao;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.exception.GenreDoesNotExistException;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao for working with Genres ")
@DataJpaTest
@Import({GenreDaoJpa.class})
class GenreDaoJpaTest {

    public static final long EXPECTED_GENRE_COUNT = 10;

    public static final String FOR_INSERT_GENRE_NAME = "Рассказ";

    public static final long EXISTED_GENRE_ID = 1;

    public static final long EXISTED_GENRE_ID_WITHOUT_RELATED_BOOKS = 8;
    public static final long NOT_EXISTED_GENRE_ID = 555;

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private TestEntityManager em;

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
        val actualGenresCount = genreDao.count();
        assertThat(actualGenresCount).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @Test
    @DisplayName("add genre to database")
    void shouldInsertGenre() {
        val genre = Genre.builder()
                .name(FOR_INSERT_GENRE_NAME)
                .build();

        genreDao.insert(genre);
        assertThat(genre.getId()).isNotNull();

        val actualGenre = em.find(Genre.class, genre.getId());
        assertThat(actualGenre).isNotNull()
                .matches(a -> a.getName().equals(FOR_INSERT_GENRE_NAME));
    }

    @Test
    @DisplayName("return expected genre by id")
    void shouldReturnExpectedGenreById() {
        val optionalActualGenre = genreDao.getById(EXISTED_GENRE_ID);
        val expectedGenreDao = em.find(Genre.class, EXISTED_GENRE_ID);
        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenreDao);
    }

    @Test
    @DisplayName("not return expected genre by id because genre does not exist")
    void shouldNotReturnExpectedGenreById() {
        assertThat(genreDao.getById(NOT_EXISTED_GENRE_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("remove genre by id")
    void shouldCorrectDeleteGenreById() {
        val firstGenre = em.find(Genre.class, EXISTED_GENRE_ID_WITHOUT_RELATED_BOOKS);
        assertThat(firstGenre).isNotNull();
        em.detach(firstGenre);

        genreDao.deleteById(EXISTED_GENRE_ID_WITHOUT_RELATED_BOOKS);
        val deletedGenre = em.find(Genre.class, EXISTED_GENRE_ID_WITHOUT_RELATED_BOOKS);

        assertThat(deletedGenre).isNull();
    }

    @Test
    @DisplayName("not remove genre by id because genre has related books")
    void shouldNotCorrectDeleteGenreByIdBecauseGenreHasRelatedBooks() {
        assertThatCode(() -> genreDao.getById(EXISTED_GENRE_ID))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> genreDao.deleteById(EXISTED_GENRE_ID))
                .isInstanceOf(Exception.class);
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
        val actualGenreList = genreDao.getAll();

        val expectedGenreList = em.getEntityManager()
                .createQuery("select g from Genre g", Genre.class).getResultList();

        assertThat(actualGenreList)
                .hasSize(10)
                .isEqualTo(expectedGenreList);
    }

}
