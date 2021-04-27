package ru.otus.kulygin.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.CommentDoesNotExistException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName(value = "CommentController should ")
@WebMvcTest(CommentController.class)
public class CommentControllerTest extends BaseControllerTest {

    private static final String BOOK_API = "/api/book/";
    public static final String BOOK_ID = "11";
    public static final String NOT_EXISTED_BOOK_ID = "11";

    @Test
    @DisplayName(value = "add comment to book")
    public void shouldAddCommentToBook() throws Exception {
        val commentDto = CommentDto.builder()
                .commentatorName("john")
                .text("wow!")
                .build();

        mockMvc.perform(post(BOOK_API + BOOK_ID + "/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(commentDto)))
                .andExpect(status().isOk());

        verify(commentService).addCommentToBook(commentDto.getCommentatorName(), commentDto.getText(), BOOK_ID);
    }

    @Test
    @DisplayName(value = "not add comment to book because book does not exist")
    public void shouldNotAddCommentToBook_bookDoesNotExist() throws Exception {
        val commentDto = CommentDto.builder()
                .commentatorName("john")
                .text("wow!")
                .build();

        when(commentService.addCommentToBook(commentDto.getCommentatorName(), commentDto.getText(), NOT_EXISTED_BOOK_ID))
                .thenThrow(BookDoesNotExistException.class);

        mockMvc.perform(post(BOOK_API + NOT_EXISTED_BOOK_ID + "/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(commentDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("3"))
                .andExpect(jsonPath("$.message").value("Book by id has not found"));
    }


    @Test
    @DisplayName(value = "remove comment from book")
    public void shouldRemoveCommentFromBook() throws Exception {
        val comment = Comment.builder()
                .id("2")
                .commentatorName("john")
                .text("wow!")
                .build();

        mockMvc.perform(delete(BOOK_API + BOOK_ID + "/comment/" + comment.getId()))
                .andExpect(status().isOk());

        verify(commentService).removeCommentFromBook(comment.getId(), BOOK_ID);
    }

    @Test
    @DisplayName(value = "remove comment from book because comment does not exist")
    public void shouldNotRemoveCommentFromBook_commentDoesNotExist() throws Exception {
        val comment = Comment.builder()
                .id("2")
                .text("wow!")
                .build();

        when(commentService.removeCommentFromBook(comment.getId(), BOOK_ID)).thenThrow(CommentDoesNotExistException.class);

        mockMvc.perform(delete(BOOK_API + BOOK_ID + "/comment/" + comment.getId()))
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

        when(commentService.removeCommentFromBook(comment.getId(), BOOK_ID)).thenThrow(BookDoesNotExistException.class);

        mockMvc.perform(delete(BOOK_API + BOOK_ID + "/comment/" + comment.getId()))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("3"))
                .andExpect(jsonPath("$.message").value("Book by id has not found"));

    }

}
