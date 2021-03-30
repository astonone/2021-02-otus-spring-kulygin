package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.dao.BookDao;
import ru.otus.kulygin.dao.CommentDao;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.service.BookService;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BookServiceImpl.class)
@DisplayName(value = "BookServiceImpl should ")
class BookServiceImplTest {

    public static final long EXPECTED_BOOK_COUNT = 5;
    public static final long NOT_EXISTED_BOOK_ID = 6;

    public static final long FOR_INSERT_BOOK_ID = 11;
    public static final long EXISTED_COMMENT_ID = 1;

    @Autowired
    private BookService bookService;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private CommentDao commentDao;

    @Test
    @DisplayName("should return expected books count")
    public void shouldCountBooks() {
        when(bookDao.count()).thenReturn(EXPECTED_BOOK_COUNT);

        assertThat(bookService.count()).isEqualTo(EXPECTED_BOOK_COUNT);
        verify(bookDao).count();
    }

    @Test
    @DisplayName("add book to database")
    public void shouldInsertBook() {
        val book = Book.builder()
                .id(FOR_INSERT_BOOK_ID)
                .build();

        bookService.insert(book);

        verify(bookDao).insert(book);
    }

    @Test
    @DisplayName("return expected book by id")
    public void shouldGetBookById() {
        val book = Optional.of(Book.builder()
                .id(FOR_INSERT_BOOK_ID)
                .build());

        when(bookDao.getById(FOR_INSERT_BOOK_ID)).thenReturn(book);

        val result = bookService.getById(FOR_INSERT_BOOK_ID);

        assertThat(result).isEqualTo(book);
        verify(bookDao).getById(FOR_INSERT_BOOK_ID);
    }

    @Test
    @DisplayName("not return expected book by id because book does not exist")
    void shouldNotReturnExpectedBookById() {
        when(bookDao.getById(NOT_EXISTED_BOOK_ID)).thenReturn(Optional.empty());

        assertThat(bookService.getById(NOT_EXISTED_BOOK_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("return list of books")
    public void shouldGetAllBook() {
        val bookList = Collections.singletonList(Book.builder().build());
        when(bookDao.getAll()).thenReturn(bookList);

        val all = bookService.getAll();
        assertThat(all).isEqualTo(bookList);
        verify(bookDao).getAll();
    }

    @Test
    @DisplayName("remove book by id")
    void shouldCorrectDeleteBookById() {
        bookDao.deleteById(FOR_INSERT_BOOK_ID);

        verify(bookDao).deleteById(FOR_INSERT_BOOK_ID);
    }

    @Test
    @DisplayName("not remove book by id because book does not exist")
    void shouldNotCorrectDeleteBookByIdBecauseBookDoesNotExist() {
        doThrow(BookDoesNotExistException.class).when(bookDao).deleteById(NOT_EXISTED_BOOK_ID);

        assertThatThrownBy(() -> bookService.deleteById(NOT_EXISTED_BOOK_ID))
                .isInstanceOf(BookDoesNotExistException.class);
    }

    @Test
    @DisplayName("should add comment to book")
    public void shouldAddCommentToBook() {
        val book = Optional.of(Book.builder().id(FOR_INSERT_BOOK_ID).build());

        when(bookDao.getById(FOR_INSERT_BOOK_ID)).thenReturn(book);

        bookService.addCommentToBook("vasya", "lol", book.get());

        commentDao.insert(any(Comment.class));
        verify(bookDao).getById(FOR_INSERT_BOOK_ID);
    }

    @Test
    @DisplayName("should remove comment from book")
    public void shouldRemoveCommentFromBook() {
        val comment = Optional.of(Comment.builder()
                .book(Book.builder()
                        .id(FOR_INSERT_BOOK_ID)
                        .build())
                .build());

        when(bookDao.getById(FOR_INSERT_BOOK_ID)).thenReturn(Optional.of(Book.builder().build()));

        when(commentDao.getById(EXISTED_COMMENT_ID)).thenReturn(comment);

        bookService.removeCommentFromBook(EXISTED_COMMENT_ID);

        verify(commentDao).getById(EXISTED_COMMENT_ID);
        verify(commentDao).deleteById(EXISTED_COMMENT_ID);
        verify(bookDao).getById(FOR_INSERT_BOOK_ID);
    }

    @Test
    @DisplayName("should not remove comment from book because comment does not exist")
    public void shouldNotCorrectDeleteCommentByIdBecauseCommentDoesNotExist() {
        when(commentDao.getById(EXISTED_COMMENT_ID)).thenThrow(CommentDoesNotExistException.class);

        assertThatThrownBy(() -> bookService.removeCommentFromBook(EXISTED_COMMENT_ID))
                .isInstanceOf(CommentDoesNotExistException.class);
    }

}
