package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.repository.AuthorRepository;
import ru.otus.kulygin.service.AuthorService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AuthorServiceImpl.class)
@DisplayName(value = "AuthorServiceImpl should ")
class AuthorServiceImplTest {

    public static final long EXPECTED_AUTHOR_COUNT = 5;
    public static final String NOT_EXISTED_AUTHOR_ID = "6";

    public static final String FOR_INSERT_AUTHOR_ID = "11";
    public static final String FOR_INSERT_AUTHOR_FIRST_NAME = "Ася";
    public static final String FOR_INSERT_AUTHOR_LAST_NAME = "Казанцева";

    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private MappingService mappingService;

    @Test
    @DisplayName("should return expected authors count")
    public void shouldCountAuthors() {
        when(authorRepository.count()).thenReturn(EXPECTED_AUTHOR_COUNT);

        assertThat(authorService.count()).isEqualTo(EXPECTED_AUTHOR_COUNT);
        verify(authorRepository).count();
    }

    @Test
    @DisplayName("add author to database")
    public void shouldInsertAuthor() {
        val authorDto = AuthorDto.builder()
                .firstName(FOR_INSERT_AUTHOR_FIRST_NAME)
                .lastName(FOR_INSERT_AUTHOR_LAST_NAME)
                .build();

        val author = Author.builder()
                .firstName(FOR_INSERT_AUTHOR_FIRST_NAME)
                .lastName(FOR_INSERT_AUTHOR_LAST_NAME)
                .build();

        authorService.save(authorDto);

        verify(authorRepository).save(author);
    }

    @Test
    @DisplayName("return expected author by id")
    public void shouldGetAuthorById() {
        val author = Optional.of(Author.builder()
                .id(FOR_INSERT_AUTHOR_ID)
                .build());

        when(authorRepository.findById(FOR_INSERT_AUTHOR_ID)).thenReturn(author);

        authorService.getById(author.get().getId());

        verify(authorRepository).findById(FOR_INSERT_AUTHOR_ID);
        verify(mappingService).map(author.get(), AuthorDto.class);
    }

    @Test
    @DisplayName("not return expected author by id because author does not exist")
    void shouldNotReturnExpectedAuthorById() {
        when(authorRepository.findById(NOT_EXISTED_AUTHOR_ID)).thenReturn(Optional.empty());

        assertThat(authorService.getById(NOT_EXISTED_AUTHOR_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("return list of authors")
    public void shouldGetAllAuthors() {
        val authorList = Collections.singletonList(Author.builder().build());
        when(authorRepository.findAll()).thenReturn(authorList);

        authorService.getAll();
        verify(authorRepository).findAll();
        verify(mappingService).mapAsList(authorList, AuthorDto.class);
    }

    @Test
    @DisplayName("remove author by id")
    void shouldCorrectDeleteAuthorById() {
        when(authorRepository.existsById(FOR_INSERT_AUTHOR_ID)).thenReturn(true);

        authorRepository.deleteById(FOR_INSERT_AUTHOR_ID);

        verify(authorRepository).deleteById(FOR_INSERT_AUTHOR_ID);
    }

    @Test
    @DisplayName("not remove author by id because author has related books")
    void shouldNotCorrectDeleteAuthorByIdBecauseAuthorHasRelatedBooks() {
        when(authorRepository.existsById(FOR_INSERT_AUTHOR_ID)).thenReturn(true);

        doThrow(RuntimeException.class).when(authorRepository).deleteById(FOR_INSERT_AUTHOR_ID);

        assertThatThrownBy(() -> authorService.deleteById(FOR_INSERT_AUTHOR_ID))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("not remove author by id because author does not exist")
    void shouldNotCorrectDeleteAuthorByIdBecauseAuthorDoesNotExist() {
        when(authorRepository.existsById(FOR_INSERT_AUTHOR_ID)).thenReturn(false);

        assertThatThrownBy(() -> authorService.deleteById(NOT_EXISTED_AUTHOR_ID))
                .isInstanceOf(AuthorDoesNotExistException.class);
    }

}
