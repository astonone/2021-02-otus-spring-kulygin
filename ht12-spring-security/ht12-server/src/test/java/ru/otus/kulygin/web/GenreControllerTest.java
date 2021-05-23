package ru.otus.kulygin.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.web.config.SpringSecurityWebTestConfig;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SpringSecurityWebTestConfig.class)
@DisplayName(value = "GenreController should ")
@WebMvcTest(GenreController.class)
public class GenreControllerTest extends BaseControllerTest {

    private static final String GENRE_API = "/api/genre/";

    @Test
    @DisplayName(value = "return genre list")
    @WithMockUser(username = "astonone")
    public void shouldGetGenresList() throws Exception {
        val genre = GenreDto.builder()
                .id("1")
                .name("Триллер")
                .build();

        when(genreService.getAll()).thenReturn(Collections.singletonList(genre));

        mockMvc.perform(get(GENRE_API))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "remove genre by id")
    @WithMockUser(username = "astonone")
    public void shouldRemoveGenreById() throws Exception {
        val genre = GenreDto.builder()
                .id("1")
                .name("Триллер")
                .build();

        mockMvc.perform(delete(GENRE_API + genre.getId()))
                .andExpect(status().isOk());

        verify(genreService).deleteById(genre.getId());
    }

    @Test
    @DisplayName(value = "not remove genre by id")
    @WithMockUser(username = "astonone")
    public void shouldNotRemoveGenreById() throws Exception {
        val genre = GenreDto.builder()
                .id("2")
                .name("Триллер")
                .build();

        doThrow(RuntimeException.class).when(genreService).deleteById(genre.getId());

        mockMvc.perform(delete(GENRE_API + genre.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName(value = "create new genre")
    @WithMockUser(username = "astonone")
    public void shouldCreateGenre() throws Exception {
        val genreDto = GenreDto.builder()
                .name("Драма")
                .build();

        val genreSaved = GenreDto.builder()
                .id("1")
                .name("Драма")
                .build();

        when(genreService.save(genreDto)).thenReturn(genreSaved);

        mockMvc.perform(post(GENRE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(genreDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Драма"));
    }

    @Test
    @DisplayName(value = "update existed genre")
    @WithMockUser(username = "astonone")
    public void shouldUpdateGenre() throws Exception {
        val genreDto = GenreDto.builder()
                .id("1")
                .name("Драма")
                .build();

        val genreSaved = GenreDto.builder()
                .id("1")
                .name("Драма")
                .build();

        when(genreService.save(genreDto)).thenReturn(genreSaved);

        mockMvc.perform(post(GENRE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(genreDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Драма"));
    }

    @Test
    @DisplayName(value = "not update existed genre")
    @WithMockUser(username = "astonone")
    public void shouldNotUpdateGenre() throws Exception {
        val genreForSaveDto = GenreDto.builder()
                .id("1")
                .name("Драма")
                .build();

        when(genreService.save(genreForSaveDto)).thenThrow(GenreDoesNotExistException.class);

        mockMvc.perform(post(GENRE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(genreForSaveDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("1"))
                .andExpect(jsonPath("$.message").value("Genre by id has not found"));
    }

}
