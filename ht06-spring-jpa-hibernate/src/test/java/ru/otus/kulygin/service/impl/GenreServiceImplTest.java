package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.dao.GenreDao;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.exception.RelatedEntityException;
import ru.otus.kulygin.service.GenreService;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GenreServiceImpl.class)
@DisplayName(value = "GenreServiceImpl should ")
class GenreServiceImplTest {

    public static final long EXPECTED_GENRE_COUNT = 5;
    public static final long NOT_EXISTED_GENRE_ID = 6;

    public static final long FOR_INSERT_GENRE_ID = 11;
    public static final String FOR_INSERT_GENRE_NAME = "Рассказ";

    @Autowired
    private GenreService genreService;

    @MockBean
    MappingService mappingService;

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
        val genre = Genre.builder()
                .id(FOR_INSERT_GENRE_ID)
                .name(FOR_INSERT_GENRE_NAME)
                .build();

        genreService.insert(genre);

        verify(genreDao).insert(genre);
    }

    @Test
    @DisplayName("return expected genre by id")
    public void shouldGetGenreById() {
        val genre = Optional.of(Genre.builder()
                .id(FOR_INSERT_GENRE_ID)
                .name(FOR_INSERT_GENRE_NAME)
                .build());
        when(genreDao.getById(FOR_INSERT_GENRE_ID)).thenReturn(genre);

        genreService.getById(FOR_INSERT_GENRE_ID);

        verify(genreDao).getById(FOR_INSERT_GENRE_ID);
        verify(mappingService).map(genre.get(), GenreDto.class);
    }

    @Test
    @DisplayName("not return expected genre by id because genre does not exist")
    void shouldNotReturnExpectedGenreById() {
        when(genreDao.getById(NOT_EXISTED_GENRE_ID)).thenReturn(Optional.empty());

        assertThat(genreDao.getById(NOT_EXISTED_GENRE_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("return list of genres")
    public void shouldGetAllGenres() {
        val genreList = Collections.singletonList(Genre.builder().build());
        when(genreDao.getAll()).thenReturn(genreList);

        genreService.getAll();

        verify(genreDao).getAll();
        verify(mappingService).mapAsList(genreList, GenreDto.class);
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
        doThrow(RuntimeException.class).when(genreDao).deleteById(FOR_INSERT_GENRE_ID);

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
