package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.repository.BookRepository;
import ru.otus.kulygin.repository.CommentRepository;
import ru.otus.kulygin.service.CommentService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    @MockBean
    private BookRepository bookRepository;

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

    @Test
    @DisplayName("should add comment to book")
    public void shouldAddCommentToBook() {
        when(bookRepository.findById(BOOK_WITH_COMMENT_ID)).thenReturn(Optional.of(Book.builder().build()));
        when(mappingService.map(any(Book.class), eq(BookDto.class))).thenReturn(BookDto.builder().build());

        commentService.addCommentToBook("vasya", "lol", BOOK_WITH_COMMENT_ID);

        verify(commentRepository).save(any(Comment.class));
        verify(bookRepository, times(2)).findById(BOOK_WITH_COMMENT_ID);
        verify(mappingService).map(any(Book.class), eq(BookDto.class));
    }

    @Test
    @DisplayName("should remove comment from book")
    public void shouldRemoveCommentFromBook() {
        when(bookRepository.findById(BOOK_WITH_COMMENT_ID)).thenReturn(Optional.of(Book.builder().build()));
        when(commentRepository.findById(EXISTED_COMMENT_ID)).thenReturn(Optional.of(Comment.builder().build()));
        when(mappingService.map(any(Book.class), eq(BookDto.class))).thenReturn(BookDto.builder().build());

        commentService.removeCommentFromBook(EXISTED_COMMENT_ID, BOOK_WITH_COMMENT_ID);

        verify(commentRepository).deleteById(EXISTED_COMMENT_ID);
        verify(bookRepository, times(2)).findById(BOOK_WITH_COMMENT_ID);
        verify(mappingService).map(any(Book.class), eq(BookDto.class));
    }

    @Test
    @DisplayName("should not remove comment from book because comment does not exist")
    public void shouldNotCorrectDeleteCommentByIdBecauseCommentDoesNotExist() {
        when(bookRepository.findById(BOOK_WITH_COMMENT_ID)).thenReturn(Optional.of(Book.builder().build()));
        when(commentRepository.findById(EXISTED_COMMENT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.removeCommentFromBook(EXISTED_COMMENT_ID, BOOK_WITH_COMMENT_ID))
                .isInstanceOf(CommentDoesNotExistException.class);
    }

    @Test
    @DisplayName("should not remove comment from book because book does not exist")
    public void shouldNotCorrectDeleteCommentByIdBecauseBookDoesNotExist() {
        when(bookRepository.findById(BOOK_WITH_COMMENT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.removeCommentFromBook(EXISTED_COMMENT_ID, BOOK_WITH_COMMENT_ID))
                .isInstanceOf(BookDoesNotExistException.class);
    }

}