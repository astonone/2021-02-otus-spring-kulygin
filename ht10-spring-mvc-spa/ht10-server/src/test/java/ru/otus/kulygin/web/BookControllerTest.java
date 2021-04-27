package ru.otus.kulygin.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.exception.CommentDoesNotExistException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName(value = "BookController should ")
@WebMvcTest(BookController.class)
public class BookControllerTest extends BaseControllerTest {

    private static final String BOOK_API = "/api/book/";

    @Test
    @DisplayName(value = "get book by id")
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

        val genre = Genre.builder()
                .id("2")
                .name("Антиутопия")
                .build();

        val author = Author.builder()
                .id("3")
                .firstName("Джордж")
                .firstName("Оруэл")
                .build();

        val bookForSave = Book.builder()
                .title("1984")
                .genre(genre)
                .author(author)
                .build();

        val bookSaved = BookDto.builder()
                .id("1")
                .title("1984")
                .genre(genreDto)
                .author(authorDto)
                .comments(new ArrayList<>())
                .build();

        when(bookService.save(bookForSave)).thenReturn(bookSaved);
        when(authorService.getById(authorDto.getId())).thenReturn(Optional.of(authorDto));
        when(genreService.getById(genreDto.getId())).thenReturn(Optional.of(genreDto));
        when(mappingService.map(genreDto, Genre.class)).thenReturn(genre);
        when(mappingService.map(authorDto, Author.class)).thenReturn(author);

        mockMvc.perform(post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("1984"));
    }

    @Test
    @DisplayName(value = "update book")
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

        val genre = Genre.builder()
                .id("2")
                .name("Антиутопия")
                .build();

        val author = Author.builder()
                .id("3")
                .firstName("Джордж")
                .lastName("Оруэл")
                .build();

        val bookForSave = Book.builder()
                .id("1")
                .title("1985")
                .genre(genre)
                .author(author)
                .build();

        val bookSaved = BookDto.builder()
                .id("1")
                .title("1985")
                .genre(genreDto)
                .author(authorDto)
                .comments(new ArrayList<>())
                .build();

        when(bookService.save(bookForSave)).thenReturn(bookSaved);
        when(authorService.getById(authorDto.getId())).thenReturn(Optional.of(authorDto));
        when(genreService.getById(genreDto.getId())).thenReturn(Optional.of(genreDto));
        when(bookService.getById(bookDto.getId())).thenReturn(Optional.of(bookDto));
        when(mappingService.map(genreDto, Genre.class)).thenReturn(genre);
        when(mappingService.map(authorDto, Author.class)).thenReturn(author);
        when(bookService.save(bookForSave)).thenReturn(bookSaved);

        mockMvc.perform(post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("1985"));
    }

    @Test
    @DisplayName(value = "not update book")
    public void shouldNotUpdateBook() throws Exception {
        val bookDto = BookDto.builder()
                .id("1")
                .title("1984")
                .build();

        mockMvc.perform(post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bookDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("3"))
                .andExpect(jsonPath("$.message").value("Book by id has not found"));
    }

    @Test
    @DisplayName(value = "add comment to book")
    public void shouldAddCommentToBook() throws Exception {
        val bookDto = BookDto.builder()
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

        val book = Book.builder()
                .id("1")
                .title("1984")
                .genre(Genre.builder()
                        .id("2")
                        .name("Антиутопия")
                        .build())
                .author(Author.builder()
                        .id("3")
                        .firstName("Джордж")
                        .lastName("Оруэл")
                        .build())
                .build();

        val commentDto = CommentDto.builder()
                .commentatorName("john")
                .text("wow!")
                .build();

        when(bookService.getById(book.getId())).thenReturn(Optional.of(bookDto));
        when(mappingService.map(bookDto, Book.class)).thenReturn(book);

        mockMvc.perform(post(BOOK_API + book.getId() + "/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(commentDto)))
                .andExpect(status().isOk());

        verify(bookService).addCommentToBook(commentDto.getCommentatorName(), commentDto.getText(), book);
    }

    @Test
    @DisplayName(value = "not add comment to book because book does not exist")
    public void shouldNotAddCommentToBook_bookDoesNotExist() throws Exception {
        val commentDto = CommentDto.builder()
                .commentatorName("john")
                .text("wow!")
                .build();

        mockMvc.perform(post(BOOK_API + "2/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(commentDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("3"))
                .andExpect(jsonPath("$.message").value("Book by id has not found"));
    }

    @Test
    @DisplayName(value = "remove comment from book")
    public void shouldRemoveCommentFromBook() throws Exception {
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
                        .firstName("Оруэл")
                        .build())
                .comments(new ArrayList<>())
                .build();

        val comment = Comment.builder()
                .id("2")
                .commentatorName("john")
                .text("wow!")
                .build();

        when(bookService.getById(book.getId())).thenReturn(Optional.of(book));

        mockMvc.perform(delete(BOOK_API + book.getId() + "/comment/" + comment.getId()))
                .andExpect(status().isOk());

        verify(bookService).removeCommentFromBook(comment.getId());
    }

    @Test
    @DisplayName(value = "remove comment from book because comment does not exist")
    public void shouldNotRemoveCommentFromBook_commentDoesNotExist() throws Exception {
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
                        .firstName("Оруэл")
                        .build())
                .comments(new ArrayList<>())
                .build();

        val comment = Comment.builder()
                .id("2")
                .text("wow!")
                .build();

        when(bookService.getById(book.getId())).thenReturn(Optional.of(book));
        when(bookService.removeCommentFromBook(comment.getId())).thenThrow(CommentDoesNotExistException.class);

        mockMvc.perform(delete(BOOK_API + "1/comment/2"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("4"))
                .andExpect(jsonPath("$.message").value("Comment by id has not found"));
    }

    @Test
    @DisplayName(value = "remove comment from book because book does not exist")
    public void shouldNotRemoveCommentFromBook_bookDoesNotExist() throws Exception {
        val comment = Comment.builder()
                .id("2")
                .text("wow!")
                .build();

        mockMvc.perform(delete(BOOK_API + "1/comment/2"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("3"))
                .andExpect(jsonPath("$.message").value("Book by id has not found"));

    }

}
