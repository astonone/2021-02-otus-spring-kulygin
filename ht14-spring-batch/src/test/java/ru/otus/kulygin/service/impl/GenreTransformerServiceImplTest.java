package ru.otus.kulygin.service.impl;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.domain.rdb.Genre;
import ru.otus.kulygin.repository.mongodb.GenreDocumentRepository;
import ru.otus.kulygin.service.GenreTransformerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GenreTransformerServiceImpl.class)
class GenreTransformerServiceImplTest {

    public static final String NAME = "name";

    @Autowired
    private GenreTransformerService genreTransformerService;

    @MockBean
    private GenreDocumentRepository genreDocumentRepository;

    @Test
    public void shouldTransformFromJDBCDomainModelToMongoDomainModel() {
        val genre = Genre.builder()
                .name(NAME)
                .build();

        when(genreDocumentRepository.existsByName(genre.getName()))
                .thenReturn(false);

        val result = genreTransformerService.transform(genre);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(NAME);
    }

    @Test
    public void shouldNotTransformFromJDBCDomainModelToMongoDomainModel() {
        val genre = Genre.builder()
                .name(NAME)
                .build();

        when(genreDocumentRepository.existsByName(genre.getName()))
                .thenReturn(true);

        val result = genreTransformerService.transform(genre);

        assertThat(result).isNull();
    }

}