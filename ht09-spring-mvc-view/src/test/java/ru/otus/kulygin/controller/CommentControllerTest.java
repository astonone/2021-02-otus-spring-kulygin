package ru.otus.kulygin.controller;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.web.CommentController;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName(value = "CommentController should ")
@WebMvcTest(CommentController.class)
public class CommentControllerTest extends BaseControllerTest {

    @Test
    @DisplayName(value = "load comments page")
    public void shouldLoadBookCommentsPage() throws Exception {
        mockMvc.perform(get("/book-comments?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "delete comment")
    public void shouldDeleteBookComment() throws Exception {
        mockMvc.perform(post("/delete-comment?commentId=1&bookId=1"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName(value = "load delete comment page")
    public void shouldLoadBookCommentCreatePage() throws Exception {
        mockMvc.perform(get("/book-comment-create?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "create comment")
    public void shouldCreateBookComment() throws Exception {
        val authorDto = AuthorDto.builder()
                .id("1")
                .firstName("Alexander")
                .lastName("Pushkin")
                .build();
        val author = Author.builder()
                .id("1")
                .firstName("Alexander")
                .lastName("Pushkin")
                .build();
        val genreDto = GenreDto.builder()
                .id("1")
                .name("Triller")
                .build();
        val genre = Genre.builder()
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
        val book = Book.builder()
                .id("1")
                .title("Capitan's daughter")
                .genre(genre)
                .author(author)
                .build();

        when(genreService.getById("1")).thenReturn(Optional.of(genreDto));
        when(authorService.getById("1")).thenReturn(Optional.of(authorDto));
        when(bookService.getById("1")).thenReturn(Optional.of(bookDto));
        when(mappingService.map(bookDto, Book.class)).thenReturn(book);

        mockMvc.perform(post("/book-comment-create?bookId=1")
                .flashAttr("comment", Comment.builder()
                        .id("1")
                        .build()))
                .andExpect(status().is3xxRedirection());
    }

}
