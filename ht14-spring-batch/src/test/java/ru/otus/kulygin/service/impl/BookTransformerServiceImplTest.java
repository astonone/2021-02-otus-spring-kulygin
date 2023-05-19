package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.domain.mongodb.AuthorDocument;
import ru.otus.kulygin.domain.mongodb.GenreDocument;
import ru.otus.kulygin.domain.rdb.Author;
import ru.otus.kulygin.domain.rdb.Book;
import ru.otus.kulygin.domain.rdb.Genre;
import ru.otus.kulygin.repository.mongodb.AuthorDocumentRepository;
import ru.otus.kulygin.repository.mongodb.BookDocumentRepository;
import ru.otus.kulygin.repository.mongodb.GenreDocumentRepository;
import ru.otus.kulygin.service.BookTransformerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static ru.otus.kulygin.service.impl.AuthorTransformerServiceImplTest.FIRST_NAME;
import static ru.otus.kulygin.service.impl.AuthorTransformerServiceImplTest.LAST_NAME;
import static ru.otus.kulygin.service.impl.GenreTransformerServiceImplTest.NAME;

@SpringBootTest(classes = BookTransformerServiceImpl.class)
class BookTransformerServiceImplTest {

    public static final String TITLE = "title";
    @Autowired
    private BookTransformerService bookTransformerService;

    @MockBean
    private BookDocumentRepository bookDocumentRepository;
    @MockBean
    private GenreDocumentRepository genreDocumentRepository;
    @MockBean
    private AuthorDocumentRepository authorDocumentRepository;

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

        when(bookDocumentRepository.existsByTitleAndAuthor_FirstNameAndAuthor_LastName(book.getTitle(),
                book.getAuthor().getFirstName(), book.getAuthor().getLastName())).thenReturn(false);
        when(genreDocumentRepository.findByName(book.getGenre().getName())).thenReturn(GenreDocument.builder()
                .name(NAME)
                .build());
        when(authorDocumentRepository.findByFirstNameAndLastName(
                book.getAuthor().getFirstName(), book.getAuthor().getLastName())).thenReturn(AuthorDocument.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build());

        val result = bookTransformerService.transform(book);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(TITLE);
        assertThat(result.getGenre().getName()).isEqualTo(NAME);
        assertThat(result.getAuthor().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(result.getAuthor().getLastName()).isEqualTo(LAST_NAME);


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

        when(bookDocumentRepository.existsByTitleAndAuthor_FirstNameAndAuthor_LastName(book.getTitle(),
                book.getAuthor().getFirstName(), book.getAuthor().getLastName())).thenReturn(true);

        val result = bookTransformerService.transform(book);

        assertThat(result).isNull();
    }
}