package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.dao.AuthorDao;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.exception.RelatedEntityException;
import ru.otus.kulygin.service.AuthorService;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AuthorServiceImpl.class)
@DisplayName(value = "AuthorServiceImpl should ")
class AuthorServiceImplTest {

    public static final long EXPECTED_AUTHOR_COUNT = 5;
    public static final long NOT_EXISTED_AUTHOR_ID = 6;

    public static final long FOR_INSERT_AUTHOR_ID = 11;
    public static final String FOR_INSERT_AUTHOR_FIRST_NAME = "Ася";
    public static final String FOR_INSERT_AUTHOR_LAST_NAME = "Казанцева";

    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    MappingService mappingService;

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
        val author = Author.builder()
                .firstName(FOR_INSERT_AUTHOR_FIRST_NAME)
                .lastName(FOR_INSERT_AUTHOR_LAST_NAME)
                .build();

        authorService.insert(author);

        verify(authorDao).insert(author);
    }

    @Test
    @DisplayName("return expected author by id")
    public void shouldGetAuthorById() {
        val author = Optional.of(Author.builder()
                .id(FOR_INSERT_AUTHOR_ID)
                .build());

        when(authorDao.getById(FOR_INSERT_AUTHOR_ID)).thenReturn(author);

        authorService.getById(author.get().getId());

        verify(authorDao).getById(FOR_INSERT_AUTHOR_ID);
        verify(mappingService).map(author.get(), AuthorDto.class);
    }

    @Test
    @DisplayName("not return expected author by id because author does not exist")
    void shouldNotReturnExpectedAuthorById() {
        when(authorDao.getById(NOT_EXISTED_AUTHOR_ID)).thenReturn(Optional.empty());

        assertThat(authorService.getById(NOT_EXISTED_AUTHOR_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("return list of authors")
    public void shouldGetAllAuthors() {
        val authorList = Collections.singletonList(Author.builder().build());
        when(authorDao.getAll()).thenReturn(authorList);

        authorService.getAll();
        verify(authorDao).getAll();
        verify(mappingService).mapAsList(authorList, AuthorDto.class);
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
        doThrow(RuntimeException.class).when(authorDao).deleteById(FOR_INSERT_AUTHOR_ID);

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
