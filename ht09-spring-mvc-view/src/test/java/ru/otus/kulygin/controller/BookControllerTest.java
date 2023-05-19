package ru.otus.kulygin.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.web.BookController;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName(value = "BookController should ")
@WebMvcTest(BookController.class)
public class BookControllerTest extends BaseControllerTest {

    @Test
    @DisplayName(value = "load books list page")
    public void shouldGetListBooks() throws Exception {
        when(bookService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "load edit book page")
    public void shouldLoadEditBookPage() throws Exception {
        val authorDto = AuthorDto.builder()
                .id("1")
                .firstName("Alexander")
                .lastName("Pushkin")
                .build();
        val genreDto = GenreDto.builder()
                .id("1")
                .name("Triller")
                .build();

        val bookDto = BookDto.builder()
                .id("1")
                .title("Capitan's daughter")
                .genre(genreDto)
                .author(authorDto)
                .comments(new ArrayList<>())
                .build();

        when(bookService.getById("1")).thenReturn(Optional.of(bookDto));

        MvcResult result = mockMvc.perform(get("/edit-book?id=1"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()
                .contains("Capitan&#39;s daughter")).isTrue();
    }

    @Test
    @DisplayName(value = "load create book page")
    public void shouldLoadCreateBookPage() throws Exception {
        mockMvc.perform(get("/create-book"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "edit book")
    public void shouldEditBook() throws Exception {
        mockMvc.perform(post("/edit-book")
                .flashAttr("bookDto", BookDto.builder().build()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName(value = "create book")
    public void shouldCreateBook() throws Exception {
        mockMvc.perform(post("/create-book")
                .flashAttr("bookDto", BookDto.builder().build()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName(value = "delete book")
    public void shouldDeleteBook() throws Exception {
        mockMvc.perform(post("/delete-book?id=1"))
                .andExpect(status().is3xxRedirection());
    }

}
