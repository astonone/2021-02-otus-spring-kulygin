package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.repository.CommentRepository;
import ru.otus.kulygin.service.CommentService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CommentServiceImpl.class)
@DisplayName(value = "CommentServiceImpl should ")
class CommentServiceImplTest {

    public static final String EXISTED_COMMENT_ID = "1";
    public static final String NOT_EXISTED_COMMENT_ID = "99";
    public static final String BOOK_WITH_COMMENT_ID = "1";

    @Autowired
    private CommentService commentService;

    @MockBean
    private MappingService mappingService;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    @DisplayName("get comment by id")
    void shouldGetById() {
        val comment = Optional.of(Comment.builder().build());
        when(commentRepository.findById(EXISTED_COMMENT_ID)).thenReturn(comment);

        commentService.getById(EXISTED_COMMENT_ID);

        verify(commentRepository).findById(EXISTED_COMMENT_ID);
        verify(mappingService).map(comment.get(), CommentDto.class);
    }

    @Test
    @DisplayName("not get comment by id because comment does not exist")
    void shouldNotGetById() {
        when(commentRepository.findById(NOT_EXISTED_COMMENT_ID)).thenReturn(Optional.empty());

        assertThat(commentService.getById(NOT_EXISTED_COMMENT_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("add comment to database")
    void shouldInsert() {
        val comment = Comment.builder().build();

        commentService.insert(comment);

        verify(commentRepository).save(comment);
    }

    @Test
    @DisplayName("delete comment by id")
    void shouldDeleteById() {
        when(commentRepository.existsById(EXISTED_COMMENT_ID)).thenReturn(true);

        commentService.deleteById(EXISTED_COMMENT_ID);

        verify(commentRepository).deleteById(EXISTED_COMMENT_ID);
    }

    @Test
    @DisplayName("not remove comment by id because comment does not exist")
    void shouldNotDeleteById() {
        when(commentRepository.existsById(NOT_EXISTED_COMMENT_ID)).thenReturn(false);

        assertThatThrownBy(() -> commentService.deleteById(NOT_EXISTED_COMMENT_ID))
                .isInstanceOf(CommentDoesNotExistException.class);
    }

    @Test
    @DisplayName("find all comments by book id")
    void shouldFindAllByBookId() {
        val comments = Collections.singletonList(Comment.builder().build());
        when(commentRepository.findAllByBook_Id(BOOK_WITH_COMMENT_ID)).thenReturn(comments);

        commentService.findAllByBookId(BOOK_WITH_COMMENT_ID);

        verify(commentRepository).findAllByBook_Id(BOOK_WITH_COMMENT_ID);
        verify(mappingService).mapAsList(comments, CommentDto.class);
    }

}