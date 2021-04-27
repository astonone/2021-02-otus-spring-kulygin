package ru.otus.kulygin.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.web.CommentController;

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
        mockMvc.perform(post("/book-comment-create?bookId=1")
                .flashAttr("comment", Comment.builder()
                        .id("1")
                        .build()))
                .andExpect(status().is3xxRedirection());
    }

}
