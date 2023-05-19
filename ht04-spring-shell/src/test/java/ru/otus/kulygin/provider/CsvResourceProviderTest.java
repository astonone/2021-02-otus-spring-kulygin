package ru.otus.kulygin.provider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CsvResourceProvider.class)
@DisplayName(value = "CsvResourceProvider should ")
class CsvResourceProviderTest {

    @Autowired
    private CsvResourceProvider csvResourceProvider;

    @Test
    @DisplayName(value = "should get path")
    void shouldGetPath() {
        final String path = csvResourceProvider.getPath();

        assertThat(path).isEqualTo("integration-test-data/en.csv");
    }

    @Test
    @DisplayName(value = "should get delimiter")
    void shouldGetDelimiter() {
        final String delimiter = csvResourceProvider.getDelimiter();

        assertThat(delimiter).isEqualTo(";");
    }

}