package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.dao.CommentDao;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.service.CommentService;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CommentServiceImpl.class)
@DisplayName(value = "CommentServiceImpl should ")
class CommentServiceImplTest {

    public static final long EXISTED_COMMENT_ID = 1;
    public static final long NOT_EXISTED_COMMENT_ID = 99;
    public static final long BOOK_WITH_COMMENT_ID = 1;

    @Autowired
    private CommentService commentService;

    @MockBean
    MappingService mappingService;

    @MockBean
    private CommentDao commentDao;

    @Test
    @DisplayName("get comment by id")
    void shouldGetById() {
        val comment = Optional.of(Comment.builder().build());
        when(commentDao.getById(EXISTED_COMMENT_ID)).thenReturn(comment);

        commentService.getById(EXISTED_COMMENT_ID);

        verify(commentDao).getById(EXISTED_COMMENT_ID);
        verify(mappingService).map(comment.get(), CommentDto.class);
    }

    @Test
    @DisplayName("not get comment by id because comment does not exist")
    void shouldNotGetById() {
        when(commentDao.getById(NOT_EXISTED_COMMENT_ID)).thenReturn(Optional.empty());

        assertThat(commentService.getById(NOT_EXISTED_COMMENT_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("add comment to database")
    void shouldInsert() {
        val comment = Comment.builder().build();

        commentService.insert(comment);

        verify(commentDao).insert(comment);
    }

    @Test
    @DisplayName("delete comment by id")
    void shouldDeleteById() {
        commentService.deleteById(EXISTED_COMMENT_ID);

        verify(commentDao).deleteById(EXISTED_COMMENT_ID);
    }

    @Test
    @DisplayName("not remove comment by id because comment does not exist")
    void shouldNotDeleteById() {
        doThrow(CommentDoesNotExistException.class).when(commentDao).deleteById(NOT_EXISTED_COMMENT_ID);

        assertThatThrownBy(() -> commentService.deleteById(NOT_EXISTED_COMMENT_ID))
                .isInstanceOf(CommentDoesNotExistException.class);
    }

    @Test
    @DisplayName("find all comments by book id")
    void shouldFindAllByBookId() {
        val comments = Collections.singletonList(Comment.builder().build());
        when(commentDao.findAllByBookId(BOOK_WITH_COMMENT_ID)).thenReturn(comments);

        commentService.findAllByBookId(BOOK_WITH_COMMENT_ID);

        verify(commentDao).findAllByBookId(BOOK_WITH_COMMENT_ID);
        verify(mappingService).mapAsList(comments, CommentDto.class);
    }

}