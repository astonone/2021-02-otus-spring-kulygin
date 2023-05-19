package ru.otus.kulygin.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.web.GenreController;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName(value = "GenreController should ")
@WebMvcTest(GenreController.class)
public class GenreControllerTest extends BaseControllerTest {

    @Test
    @DisplayName(value = "load genre list page")
    public void shouldGetListGenres() throws Exception {
        when(genreService.getAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/genre-list"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "load edit genre page")
    public void shouldLoadEditGenrePage() throws Exception {
        val genreDto = GenreDto.builder()
                .id("1")
                .name("Triller")
                .build();

        when(genreService.getById("1")).thenReturn(Optional.of(genreDto));

        MvcResult result = mockMvc.perform(get("/edit-genre?id=1"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()
                .contains("Triller")).isTrue();
    }

    @Test
    @DisplayName(value = "load create genre page")
    public void shouldLoadCreateGenrePage() throws Exception {
        mockMvc.perform(get("/create-genre"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "edit genre")
    public void shouldEditGenre() throws Exception {
        mockMvc.perform(post("/edit-genre")
                .flashAttr("genre", GenreDto.builder().build()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName(value = "create genre")
    public void shouldCreateGenre() throws Exception {
        mockMvc.perform(post("/create-genre")
                .flashAttr("genre", GenreDto.builder().build()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName(value = "delete genre")
    public void shouldDeleteGenre() throws Exception {
        mockMvc.perform(post("/delete-genre?id=1"))
                .andExpect(status().is3xxRedirection());
    }

}
