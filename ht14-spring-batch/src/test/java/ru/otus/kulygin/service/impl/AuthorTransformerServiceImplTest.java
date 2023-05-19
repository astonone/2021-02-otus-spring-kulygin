package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.domain.rdb.Author;
import ru.otus.kulygin.repository.mongodb.AuthorDocumentRepository;
import ru.otus.kulygin.service.AuthorTransformerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AuthorTransformerServiceImpl.class)
class AuthorTransformerServiceImplTest {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";

    @Autowired
    private AuthorTransformerService authorTransformerService;

    @MockBean
    private AuthorDocumentRepository authorDocumentRepository;

    @Test
    public void shouldTransformFromJDBCDomainModelToMongoDomainModel() {
        val author = Author.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        when(authorDocumentRepository.existsByFirstNameAndLastName(author.getFirstName(), author.getLastName()))
                .thenReturn(false);

        val result = authorTransformerService.transform(author);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(result.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    public void shouldNotTransformFromJDBCDomainModelToMongoDomainModel() {
        val author = Author.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        when(authorDocumentRepository.existsByFirstNameAndLastName(author.getFirstName(), author.getLastName()))
                .thenReturn(true);

        val result = authorTransformerService.transform(author);

        assertThat(result).isNull();
    }
}