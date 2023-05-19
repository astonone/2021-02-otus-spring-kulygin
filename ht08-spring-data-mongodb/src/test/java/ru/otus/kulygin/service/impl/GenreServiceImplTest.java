package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.repository.GenreRepository;
import ru.otus.kulygin.service.GenreService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = GenreServiceImpl.class)
@DisplayName(value = "GenreServiceImpl should ")
class GenreServiceImplTest {

    public static final long EXPECTED_GENRE_COUNT = 5;
    public static final String NOT_EXISTED_GENRE_ID = "6";

    public static final String FOR_INSERT_GENRE_ID = "11";
    public static final String FOR_INSERT_GENRE_NAME = "Рассказ";

    @Autowired
    private GenreService genreService;

    @MockBean
    private MappingService mappingService;

    @MockBean
    private GenreRepository genreRepository;

    @Test
    @DisplayName("should return expected genres count")
    public void shouldCountGenres() {
        when(genreRepository.count()).thenReturn(EXPECTED_GENRE_COUNT);

        assertThat(genreService.count()).isEqualTo(EXPECTED_GENRE_COUNT);
        verify(genreRepository).count();
    }

    @Test
    @DisplayName("add genre to database")
    public void shouldInsertGenre() {
        val genre = Genre.builder()
                .id(FOR_INSERT_GENRE_ID)
                .name(FOR_INSERT_GENRE_NAME)
                .build();

        genreService.insert(genre);

        verify(genreRepository).save(genre);
    }

    @Test
    @DisplayName("return expected genre by id")
    public void shouldGetGenreById() {
        val genre = Optional.of(Genre.builder()
                .id(FOR_INSERT_GENRE_ID)
                .name(FOR_INSERT_GENRE_NAME)
                .build());
        when(genreRepository.findById(FOR_INSERT_GENRE_ID)).thenReturn(genre);

        genreService.getById(FOR_INSERT_GENRE_ID);

        verify(genreRepository).findById(FOR_INSERT_GENRE_ID);
        verify(mappingService).map(genre.get(), GenreDto.class);
    }

    @Test
    @DisplayName("not return expected genre by id because genre does not exist")
    void shouldNotReturnExpectedGenreById() {
        when(genreRepository.findById(NOT_EXISTED_GENRE_ID)).thenReturn(Optional.empty());

        assertThat(genreRepository.findById(NOT_EXISTED_GENRE_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("return list of genres")
    public void shouldGetAllGenres() {
        val genreList = Collections.singletonList(Genre.builder().build());
        when(genreRepository.findAll()).thenReturn(genreList);

        genreService.getAll();

        verify(genreRepository).findAll();
        verify(mappingService).mapAsList(genreList, GenreDto.class);
    }

    @Test
    @DisplayName("remove genre by id")
    void shouldCorrectDeleteGenreById() {
        when(genreRepository.existsById(FOR_INSERT_GENRE_ID)).thenReturn(true);

        genreRepository.deleteById(FOR_INSERT_GENRE_ID);

        verify(genreRepository).deleteById(FOR_INSERT_GENRE_ID);
    }

    @Test
    @DisplayName("not remove genre by id because genre has related books")
    void shouldNotCorrectDeleteGenreByIdBecauseGenreHasRelatedBooks() {
        when(genreRepository.existsById(FOR_INSERT_GENRE_ID)).thenReturn(true);
        doThrow(RuntimeException.class).when(genreRepository).deleteById(FOR_INSERT_GENRE_ID);

        assertThatThrownBy(() -> genreService.deleteById(FOR_INSERT_GENRE_ID))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("not remove genre by id because genre does not exist")
    void shouldNotCorrectDeleteGenreByIdBecauseGenreDoesNotExist() {
        when(genreRepository.existsById(NOT_EXISTED_GENRE_ID)).thenReturn(false);

        assertThatThrownBy(() -> genreService.deleteById(NOT_EXISTED_GENRE_ID))
                .isInstanceOf(GenreDoesNotExistException.class);
    }

}
