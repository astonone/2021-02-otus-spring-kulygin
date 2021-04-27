package ru.otus.kulygin.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName(value = "AuthorController should ")
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest extends BaseControllerTest {

    private static final String AUTHOR_API = "/api/author/";

    @Test
    @DisplayName(value = "get authors list")
    public void shouldGetAuthorsList() throws Exception {
        val author = AuthorDto.builder()
                .id("1")
                .firstName("Alexander")
                .lastName("Pushkin")
                .build();

        when(authorService.getAll()).thenReturn(Collections.singletonList(author));

        mockMvc.perform(get(AUTHOR_API))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "remove author by id")
    public void shouldRemoveAuthorById() throws Exception {
        val author = AuthorDto.builder()
                .id("1")
                .firstName("Alexander")
                .lastName("Pushkin")
                .build();

        mockMvc.perform(delete(AUTHOR_API + author.getId()))
                .andExpect(status().isOk());

        verify(authorService).deleteById(author.getId());
    }

    @Test
    @DisplayName(value = "return not remove author by id")
    public void shouldNotRemoveAuthorById() throws Exception {
        val author = AuthorDto.builder()
                .id("2")
                .firstName("Alexander")
                .lastName("Pushkin")
                .build();

        doThrow(RuntimeException.class).when(authorService).deleteById(author.getId());

        mockMvc.perform(delete(AUTHOR_API + author.getId()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName(value = "create new author")
    public void shouldCreateAuthor() throws Exception {
        val authorDto = AuthorDto.builder()
                .firstName("Alexander")
                .lastName("Pushkin")
                .build();

        val authorSaved = AuthorDto.builder()
                .id("1")
                .firstName("Alexander")
                .lastName("Pushkin")
                .build();

        when(authorService.save(authorDto)).thenReturn(authorSaved);

        mockMvc.perform(post(AUTHOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(authorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Alexander"))
                .andExpect(jsonPath("$.lastName").value("Pushkin"));
    }

    @Test
    @DisplayName(value = "update existed author")
    public void shouldUpdateAuthor() throws Exception {
        val authorDto = AuthorDto.builder()
                .id("1")
                .firstName("Alex")
                .lastName("Pushkin")
                .build();

        val authorSaved = AuthorDto.builder()
                .id("1")
                .firstName("Alex")
                .lastName("Pushkin")
                .build();

        when(authorService.save(authorDto)).thenReturn(authorSaved);

        mockMvc.perform(post(AUTHOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(authorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Alex"))
                .andExpect(jsonPath("$.lastName").value("Pushkin"));
    }

    @Test
    @DisplayName(value = "not update existed author")
    public void shouldNotUpdateAuthor() throws Exception {
        val authorDto = AuthorDto.builder()
                .id("1")
                .firstName("Alex")
                .lastName("Pushkin")
                .build();

        when(authorService.save(authorDto)).thenThrow(AuthorDoesNotExistException.class);

        mockMvc.perform(post(AUTHOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(authorDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("2"))
                .andExpect(jsonPath("$.message").value("Author by id has not found"));
    }

}
