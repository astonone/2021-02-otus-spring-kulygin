package ru.otus.kulygin.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.web.AuthorController;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName(value = "AuthorController should ")
@WebMvcTest(AuthorController.class)
public class AuthorControllerTest extends BaseControllerTest {

    @Test
    @DisplayName(value = "load author list page")
    public void shouldGetListAuthors() throws Exception {
        when(authorService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/author-list"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "load edit author page")
    public void shouldLoadEditAuthorPage() throws Exception {
        val authorDto = AuthorDto.builder()
                .id("1")
                .firstName("Alexander")
                .lastName("Pushkin")
                .build();

        when(authorService.getById("1")).thenReturn(Optional.of(authorDto));

        MvcResult result = mockMvc.perform(get("/edit-author?id=1"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()
                .contains("Pushkin")).isTrue();
    }

    @Test
    @DisplayName(value = "load create author page")
    public void shouldLoadCreateAuthorPage() throws Exception {
        mockMvc.perform(get("/create-author"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "edit author")
    public void shouldEditAuthor() throws Exception {
        mockMvc.perform(post("/edit-author")
                .flashAttr("author", AuthorDto.builder().build()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName(value = "create author")
    public void shouldCreateAuthor() throws Exception {
        mockMvc.perform(post("/create-author")
                .flashAttr("author", AuthorDto.builder().build()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName(value = "delete author")
    public void shouldDeleteAuthor() throws Exception {
        mockMvc.perform(post("/delete-author?id=1"))
                .andExpect(status().is3xxRedirection());
    }

}
