package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.kulygin.dao.AuthorDao;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.exception.EntityUniqIdException;
import ru.otus.kulygin.exception.RelatedEntityException;
import ru.otus.kulygin.service.AuthorService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AuthorServiceImpl.class)
@DisplayName(value = "AuthorServiceImpl should ")
class AuthorServiceImplTest {

    public static final int EXPECTED_AUTHOR_COUNT = 5;
    public static final long NOT_EXISTED_AUTHOR_ID = 6;

    public static final long FOR_INSERT_AUTHOR_ID = 11;
    public static final String FOR_INSERT_AUTHOR_FIRST_NAME = "Ася";
    public static final String FOR_INSERT_AUTHOR_LAST_NAME = "Казанцева";

    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorDao authorDao;

    @Test
    @DisplayName("should return expected authors count")
    public void shouldCountAuthors() {
        when(authorDao.count()).thenReturn(EXPECTED_AUTHOR_COUNT);

        assertThat(authorService.count()).isEqualTo(EXPECTED_AUTHOR_COUNT);
        verify(authorDao).count();
    }

    @Test
    @DisplayName("add author to database")
    public void shouldInsertAuthor() {
        Author author = Author.builder()
                .id(FOR_INSERT_AUTHOR_ID)
                .firstName(FOR_INSERT_AUTHOR_FIRST_NAME)
                .lastName(FOR_INSERT_AUTHOR_LAST_NAME)
                .build();

        authorService.insert(author);

        verify(authorDao).insert(author);
    }

    @Test
    @DisplayName("not add author to database because author with this id already exists")
    public void shouldNotInsertAuthor() {
        Author author = Author.builder()
                .id(FOR_INSERT_AUTHOR_ID)
                .firstName(FOR_INSERT_AUTHOR_FIRST_NAME)
                .lastName(FOR_INSERT_AUTHOR_LAST_NAME)
                .build();

        doThrow(new DuplicateKeyException("")).when(authorDao).insert(author);

        assertThatThrownBy(() -> authorService.insert(author))
                .isInstanceOf(EntityUniqIdException.class);
    }

    @Test
    @DisplayName("return expected author by id")
    public void shouldGetAuthorById() {
        Author author = Author.builder()
                .id(FOR_INSERT_AUTHOR_ID)
                .firstName(FOR_INSERT_AUTHOR_FIRST_NAME)
                .lastName(FOR_INSERT_AUTHOR_LAST_NAME)
                .build();

        when(authorDao.getById(author.getId())).thenReturn(author);

        final Author result = authorService.getById(author.getId());

        assertThat(result).isEqualTo(author);
        verify(authorDao).getById(author.getId());
    }

    @Test
    @DisplayName("not return expected author by id because author does not exist")
    void shouldNotReturnExpectedAuthorById() {
        when(authorDao.getById(NOT_EXISTED_AUTHOR_ID)).thenThrow(EmptyResultDataAccessException.class);

        assertThatThrownBy(() -> authorService.getById(NOT_EXISTED_AUTHOR_ID))
                .isInstanceOf(AuthorDoesNotExistException.class);
    }

    @Test
    @DisplayName("return list of authors")
    public void shouldGetAllAuthors() {
        List<Author> authorList = Collections.singletonList(Author.builder().build());
        when(authorDao.getAll()).thenReturn(authorList);

        final List<Author> all = authorService.getAll();
        assertThat(all).isEqualTo(authorList);
        verify(authorDao).getAll();
    }

    @Test
    @DisplayName("remove author by id")
    void shouldCorrectDeleteAuthorById() {
        authorDao.deleteById(FOR_INSERT_AUTHOR_ID);

        verify(authorDao).deleteById(FOR_INSERT_AUTHOR_ID);
    }

    @Test
    @DisplayName("not remove author by id because author has related books")
    void shouldNotCorrectDeleteAuthorByIdBecauseAuthorHasRelatedBooks() {
        doThrow(DataIntegrityViolationException.class).when(authorDao).deleteById(FOR_INSERT_AUTHOR_ID);

        assertThatThrownBy(() -> authorService.deleteById(FOR_INSERT_AUTHOR_ID))
                .isInstanceOf(RelatedEntityException.class);
    }

    @Test
    @DisplayName("not remove author by id because author does not exist")
    void shouldNotCorrectDeleteAuthorByIdBecauseAuthorDoesNotExist() {
        doThrow(AuthorDoesNotExistException.class).when(authorDao).deleteById(NOT_EXISTED_AUTHOR_ID);

        assertThatThrownBy(() -> authorService.deleteById(NOT_EXISTED_AUTHOR_ID))
                .isInstanceOf(AuthorDoesNotExistException.class);
    }

}