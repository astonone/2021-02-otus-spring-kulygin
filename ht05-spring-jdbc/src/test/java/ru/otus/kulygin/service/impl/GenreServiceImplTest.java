package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.kulygin.dao.GenreDao;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.exception.EntityUniqIdException;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.exception.RelatedEntityException;
import ru.otus.kulygin.service.GenreService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GenreServiceImpl.class)
@DisplayName(value = "GenreServiceImpl should ")
class GenreServiceImplTest {

    public static final int EXPECTED_GENRE_COUNT = 5;
    public static final long NOT_EXISTED_GENRE_ID = 6;

    public static final long FOR_INSERT_GENRE_ID = 11;
    public static final String FOR_INSERT_GENRE_NAME = "Рассказ";

    @Autowired
    private GenreService genreService;

    @MockBean
    private GenreDao genreDao;

    @Test
    @DisplayName("should return expected genres count")
    public void shouldCountGenres() {
        when(genreDao.count()).thenReturn(EXPECTED_GENRE_COUNT);

        assertThat(genreService.count()).isEqualTo(EXPECTED_GENRE_COUNT);
        verify(genreDao).count();
    }

    @Test
    @DisplayName("add genre to database")
    public void shouldInsertGenre() {
        Genre genre = Genre.builder()
                .id(FOR_INSERT_GENRE_ID)
                .name(FOR_INSERT_GENRE_NAME)
                .build();

        genreService.insert(genre);

        verify(genreDao).insert(genre);
    }

    @Test
    @DisplayName("not add genre to database because genre with this id already exists")
    public void shouldNotInsertGenre() {
        Genre genre = Genre.builder()
                .id(FOR_INSERT_GENRE_ID)
                .name(FOR_INSERT_GENRE_NAME)
                .build();

        doThrow(new DuplicateKeyException("")).when(genreDao).insert(genre);

        assertThatThrownBy(() -> genreService.insert(genre))
                .isInstanceOf(EntityUniqIdException.class);
    }

    @Test
    @DisplayName("return expected genre by id")
    public void shouldGetGenreById() {
        Genre genre = Genre.builder()
                .id(FOR_INSERT_GENRE_ID)
                .name(FOR_INSERT_GENRE_NAME)
                .build();

        when(genreDao.getById(genre.getId())).thenReturn(genre);

        final Genre result = genreService.getById(genre.getId());

        assertThat(result).isEqualTo(genre);
        verify(genreDao).getById(genre.getId());
    }

    @Test
    @DisplayName("not return expected genre by id because genre does not exist")
    void shouldNotReturnExpectedGenreById() {
        when(genreDao.getById(NOT_EXISTED_GENRE_ID)).thenThrow(EmptyResultDataAccessException.class);

        assertThatThrownBy(() -> genreService.getById(NOT_EXISTED_GENRE_ID))
                .isInstanceOf(GenreDoesNotExistException.class);
    }

    @Test
    @DisplayName("return list of genres")
    public void shouldGetAllGenres() {
        List<Genre> genreList = Collections.singletonList(Genre.builder().build());
        when(genreDao.getAll()).thenReturn(genreList);

        final List<Genre> all = genreService.getAll();
        assertThat(all).isEqualTo(genreList);
        verify(genreDao).getAll();
    }

    @Test
    @DisplayName("remove genre by id")
    void shouldCorrectDeleteGenreById() {
        genreDao.deleteById(FOR_INSERT_GENRE_ID);

        verify(genreDao).deleteById(FOR_INSERT_GENRE_ID);
    }

    @Test
    @DisplayName("not remove genre by id because genre has related books")
    void shouldNotCorrectDeleteGenreByIdBecauseGenreHasRelatedBooks() {
        doThrow(DataIntegrityViolationException.class).when(genreDao).deleteById(FOR_INSERT_GENRE_ID);

        assertThatThrownBy(() -> genreService.deleteById(FOR_INSERT_GENRE_ID))
                .isInstanceOf(RelatedEntityException.class);
    }

    @Test
    @DisplayName("not remove genre by id because genre does not exist")
    void shouldNotCorrectDeleteGenreByIdBecauseGenreDoesNotExist() {
        doThrow(GenreDoesNotExistException.class).when(genreDao).deleteById(NOT_EXISTED_GENRE_ID);

        assertThatThrownBy(() -> genreService.deleteById(NOT_EXISTED_GENRE_ID))
                .isInstanceOf(GenreDoesNotExistException.class);
    }

}