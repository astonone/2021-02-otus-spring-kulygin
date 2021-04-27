package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.exception.CommentDoesNotExistException;
import ru.otus.kulygin.repository.AuthorRepository;
import ru.otus.kulygin.repository.BookRepository;
import ru.otus.kulygin.repository.CommentRepository;
import ru.otus.kulygin.repository.GenreRepository;
import ru.otus.kulygin.service.BookService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = BookServiceImpl.class)
@DisplayName(value = "BookServiceImpl should ")
class BookServiceImplTest {

    public static final long EXPECTED_BOOK_COUNT = 5;
    public static final String NOT_EXISTED_BOOK_ID = "6";

    public static final String FOR_INSERT_BOOK_ID = "11";
    public static final String EXISTED_COMMENT_ID = "1";
    public static final String GENRE_ID = "1";
    public static final String AUTHOR_ID = "1";

    @Autowired
    private BookService bookService;

    @MockBean
    private MappingService mappingService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("should return expected books count")
    public void shouldCountBooks() {
        when(bookRepository.count()).thenReturn(EXPECTED_BOOK_COUNT);

        assertThat(bookService.count()).isEqualTo(EXPECTED_BOOK_COUNT);
        verify(bookRepository).count();
    }

    @Test
    @DisplayName("add book to database")
    public void shouldInsertBook() {
        val bookDto = BookDto.builder()
                .id(FOR_INSERT_BOOK_ID)
                .genre(GenreDto.builder().id(GENRE_ID).build())
                .author(AuthorDto.builder().id(AUTHOR_ID).build())
                .build();

        when(genreRepository.findById(bookDto.getGenre().getId())).thenReturn(Optional.of(Genre.builder().build()));
        when(authorRepository.findById(bookDto.getAuthor().getId())).thenReturn(Optional.of(Author.builder().build()));
        when(mappingService.map(bookDto, Book.class)).thenReturn(Book.builder().build());

        bookService.save(bookDto);

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("return expected book by id")
    public void shouldGetBookById() {
        val book = Optional.of(Book.builder()
                .id(FOR_INSERT_BOOK_ID)
                .build());
        when(bookRepository.findById(FOR_INSERT_BOOK_ID)).thenReturn(book);

        bookService.getById(FOR_INSERT_BOOK_ID);

        verify(bookRepository).findById(FOR_INSERT_BOOK_ID);
        verify(mappingService).map(book.get(), BookDto.class);
    }

    @Test
    @DisplayName("not return expected book by id because book does not exist")
    void shouldNotReturnExpectedBookById() {
        when(bookRepository.findById(NOT_EXISTED_BOOK_ID)).thenReturn(Optional.empty());

        assertThat(bookService.getById(NOT_EXISTED_BOOK_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("return list of books")
    public void shouldGetAllBook() {
        val bookList = Collections.singletonList(Book.builder().build());
        when(bookRepository.findAll()).thenReturn(bookList);

        bookService.getAll();

        verify(bookRepository).findAll();
        verify(mappingService).mapAsList(bookList, BookDto.class);
    }

    @Test
    @DisplayName("remove book by id")
    void shouldCorrectDeleteBookById() {
        when(bookRepository.existsById(FOR_INSERT_BOOK_ID)).thenReturn(true);

        bookRepository.deleteById(FOR_INSERT_BOOK_ID);

        verify(bookRepository).deleteById(FOR_INSERT_BOOK_ID);
    }

    @Test
    @DisplayName("not remove book by id because book does not exist")
    void shouldNotCorrectDeleteBookByIdBecauseBookDoesNotExist() {
        when(bookRepository.existsById(NOT_EXISTED_BOOK_ID)).thenReturn(false);

        assertThatThrownBy(() -> bookService.deleteById(NOT_EXISTED_BOOK_ID))
                .isInstanceOf(BookDoesNotExistException.class);
    }

    @Test
    @DisplayName("should add comment to book")
    public void shouldAddCommentToBook() {
        val book = Optional.of(Book.builder().id(FOR_INSERT_BOOK_ID).build());
        when(bookRepository.findById(FOR_INSERT_BOOK_ID)).thenReturn(book);
        when(mappingService.map(book.get(), BookDto.class)).thenReturn(BookDto.builder().build());

        bookService.addCommentToBook("vasya", "lol", FOR_INSERT_BOOK_ID);

        verify(commentRepository).save(any(Comment.class));
        verify(bookRepository, times(2)).findById(FOR_INSERT_BOOK_ID);
        verify(mappingService).map(book.get(), BookDto.class);
    }

    @Test
    @DisplayName("should remove comment from book")
    public void shouldRemoveCommentFromBook() {
        val book = Book.builder()
                .id(FOR_INSERT_BOOK_ID)
                .build();
        val comment = Optional.of(Comment.builder()
                .book(book)
                .build());
        when(bookRepository.findById(FOR_INSERT_BOOK_ID)).thenReturn(Optional.of(book));
        when(mappingService.map(book, BookDto.class)).thenReturn(BookDto.builder().build());

        when(commentRepository.findById(EXISTED_COMMENT_ID)).thenReturn(comment);

        bookService.removeCommentFromBook(EXISTED_COMMENT_ID);

        verify(commentRepository).findById(EXISTED_COMMENT_ID);
        verify(commentRepository).deleteById(EXISTED_COMMENT_ID);
        verify(bookRepository).findById(FOR_INSERT_BOOK_ID);
        verify(mappingService).map(book, BookDto.class);
    }

    @Test
    @DisplayName("should not remove comment from book because comment does not exist")
    public void shouldNotCorrectDeleteCommentByIdBecauseCommentDoesNotExist() {
        when(commentRepository.findById(EXISTED_COMMENT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.removeCommentFromBook(EXISTED_COMMENT_ID))
                .isInstanceOf(CommentDoesNotExistException.class);
    }

}
