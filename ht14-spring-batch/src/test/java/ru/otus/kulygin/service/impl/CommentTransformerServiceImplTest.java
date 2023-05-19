package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.domain.mongodb.AuthorDocument;
import ru.otus.kulygin.domain.mongodb.BookDocument;
import ru.otus.kulygin.domain.mongodb.GenreDocument;
import ru.otus.kulygin.domain.rdb.Author;
import ru.otus.kulygin.domain.rdb.Book;
import ru.otus.kulygin.domain.rdb.Comment;
import ru.otus.kulygin.domain.rdb.Genre;
import ru.otus.kulygin.repository.mongodb.BookDocumentRepository;
import ru.otus.kulygin.repository.mongodb.CommentDocumentRepository;
import ru.otus.kulygin.service.CommentTransformerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static ru.otus.kulygin.service.impl.AuthorTransformerServiceImplTest.FIRST_NAME;
import static ru.otus.kulygin.service.impl.AuthorTransformerServiceImplTest.LAST_NAME;
import static ru.otus.kulygin.service.impl.BookTransformerServiceImplTest.TITLE;
import static ru.otus.kulygin.service.impl.GenreTransformerServiceImplTest.NAME;

@SpringBootTest(classes = CommentTransformerServiceImpl.class)
class CommentTransformerServiceImplTest {

    public static final String COMM_NAME = "commName";
    public static final String TEXT = "text";
    @Autowired
    private CommentTransformerService commentTransformerService;

    @MockBean
    private BookDocumentRepository bookDocumentRepository;

    @MockBean
    private CommentDocumentRepository commentDocumentRepository;

    @Test
    public void shouldTransformFromJDBCDomainModelToMongoDomainModel() {
        val book = Book.builder()
                .title(TITLE)
                .author(Author.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .build())
                .genre(Genre.builder()
                        .name(NAME)
                        .build())
                .build();

        val bookDocument = BookDocument.builder()
                .title(TITLE)
                .author(AuthorDocument.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .build())
                .genre(GenreDocument.builder()
                        .name(NAME)
                        .build())
                .build();

        val comment = Comment.builder()
                .commentatorName(COMM_NAME)
                .text(TEXT)
                .book(book)
                .build();

        when(bookDocumentRepository.findByTitleAndAuthor_FirstNameAndAuthor_LastName(
                comment.getBook().getTitle(),
                comment.getBook().getAuthor().getFirstName(),
                comment.getBook().getAuthor().getLastName())).thenReturn(bookDocument);
        when(commentDocumentRepository.existsByCommentatorNameAndTextAndBook(comment.getCommentatorName(),
                comment.getText(), bookDocument)).thenReturn(false);

        val result = commentTransformerService.transform(comment);

        assertThat(result).isNotNull();
        assertThat(result.getCommentatorName()).isEqualTo(COMM_NAME);
        assertThat(result.getText()).isEqualTo(TEXT);
        assertThat(result.getBook().getTitle()).isEqualTo(TITLE);
        assertThat(result.getBook().getGenre().getName()).isEqualTo(NAME);
        assertThat(result.getBook().getAuthor().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(result.getBook().getAuthor().getLastName()).isEqualTo(LAST_NAME);


    }

    @Test
    public void shouldNotTransformFromJDBCDomainModelToMongoDomainModel() {
        val book = Book.builder()
                .title(TITLE)
                .author(Author.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .build())
                .genre(Genre.builder()
                        .name(NAME)
                        .build())
                .build();

        val bookDocument = BookDocument.builder()
                .title(TITLE)
                .author(AuthorDocument.builder()
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .build())
                .genre(GenreDocument.builder()
                        .name(NAME)
                        .build())
                .build();

        val comment = Comment.builder()
                .commentatorName(COMM_NAME)
                .text(TEXT)
                .book(book)
                .build();

        when(bookDocumentRepository.findByTitleAndAuthor_FirstNameAndAuthor_LastName(
                comment.getBook().getTitle(),
                comment.getBook().getAuthor().getFirstName(),
                comment.getBook().getAuthor().getLastName())).thenReturn(bookDocument);
        when(commentDocumentRepository.existsByCommentatorNameAndTextAndBook(comment.getCommentatorName(),
                comment.getText(), bookDocument)).thenReturn(true);

        val result = commentTransformerService.transform(comment);

        assertThat(result).isNull();
    }
}