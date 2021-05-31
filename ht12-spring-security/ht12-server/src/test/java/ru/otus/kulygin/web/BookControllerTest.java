package ru.otus.kulygin.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.web.config.SpringSecurityWebTestConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SpringSecurityWebTestConfig.class)
@DisplayName(value = "BookController should ")
@WebMvcTest(BookController.class)
public class BookControllerTest extends BaseControllerTest {

    private static final String BOOK_API = "/api/book/";

    @Test
    @DisplayName(value = "get book by id")
    @WithMockUser(username = "astonone")
    public void shouldGetBookById() throws Exception {
        val book = BookDto.builder()
                .id("1")
                .title("1984")
                .genre(GenreDto.builder()
                        .id("2")
                        .name("Антиутопия")
                        .build())
                .author(AuthorDto.builder()
                        .id("3")
                        .firstName("Джордж")
                        .lastName("Оруэл")
                        .build())
                .comments(new ArrayList<>())
                .build();

        when(bookService.getById(book.getId())).thenReturn(Optional.of(book));

        mockMvc.perform(get(BOOK_API + book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("1984"));
    }

    @Test
    @DisplayName(value = "get book list")
    @WithMockUser(username = "astonone")
    public void shouldGetBooksList() throws Exception {
        val book = BookDto.builder()
                .id("1")
                .title("1984")
                .genre(GenreDto.builder()
                        .id("2")
                        .name("Антиутопия")
                        .build())
                .author(AuthorDto.builder()
                        .id("3")
                        .firstName("Джордж")
                        .lastName("Оруэл")
                        .build())
                .comments(new ArrayList<>())
                .build();

        when(bookService.getAll()).thenReturn(Collections.singletonList(book));

        mockMvc.perform(get(BOOK_API))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "remove book by id")
    @WithMockUser(username = "astonone")
    public void shouldRemoveBookById() throws Exception {
        val book = BookDto.builder()
                .id("1")
                .title("1984")
                .genre(GenreDto.builder()
                        .id("2")
                        .name("Антиутопия")
                        .build())
                .author(AuthorDto.builder()
                        .id("3")
                        .firstName("Джордж")
                        .lastName("Оруэл")
                        .build())
                .comments(new ArrayList<>())
                .build();

        mockMvc.perform(delete(BOOK_API + book.getId()))
                .andExpect(status().isOk());

        verify(bookService).deleteById(book.getId());
    }

    @Test
    @DisplayName(value = "not remove book by id")
    @WithMockUser(username = "astonone")
    public void shouldCreateBook() throws Exception {
        val genreDto = GenreDto.builder()
                .id("2")
                .name("Антиутопия")
                .build();

        val authorDto = AuthorDto.builder()
                .id("3")
                .firstName("Джордж")
                .lastName("Оруэл")
                .build();

        val bookDto = BookDto.builder()
                .title("1984")
                .genre(genreDto)
                .author(authorDto)
                .comments(new ArrayList<>())
                .build();

        val bookSaved = BookDto.builder()
                .id("1")
                .title("1984")
                .genre(genreDto)
                .author(authorDto)
                .comments(new ArrayList<>())
                .build();

        when(bookService.save(bookDto)).thenReturn(bookSaved);

        mockMvc.perform(post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("1984"));
    }

    @Test
    @DisplayName(value = "update book")
    @WithMockUser(username = "astonone")
    public void shouldUpdateBook() throws Exception {
        val genreDto = GenreDto.builder()
                .id("2")
                .name("Антиутопия")
                .build();

        val authorDto = AuthorDto.builder()
                .id("3")
                .firstName("Джордж")
                .lastName("Оруэл")
                .build();

        val bookDto = BookDto.builder()
                .id("1")
                .title("1985")
                .genre(genreDto)
                .author(authorDto)
                .comments(new ArrayList<>())
                .build();

        val bookSaved = BookDto.builder()
                .id("1")
                .title("1985")
                .genre(genreDto)
                .author(authorDto)
                .comments(new ArrayList<>())
                .build();

        when(bookService.save(bookDto)).thenReturn(bookSaved);

        mockMvc.perform(post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("1985"));
    }

    @Test
    @DisplayName(value = "not update book")
    @WithMockUser(username = "astonone")
    public void shouldNotUpdateBook() throws Exception {
        val bookDto = BookDto.builder()
                .id("1")
                .title("1984")
                .build();

        when(bookService.save(bookDto)).thenThrow(BookDoesNotExistException.class);

        mockMvc.perform(post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("3"))
                .andExpect(jsonPath("$.message").value("Book by id has not found"));
    }

}
