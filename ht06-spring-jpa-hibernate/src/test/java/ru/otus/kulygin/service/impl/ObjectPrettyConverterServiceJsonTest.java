package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.service.ObjectPrettyConverterService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ObjectPrettyConverterServiceJson.class)
@DisplayName(value = "ObjectPrettyConverterServiceJson should ")
class ObjectPrettyConverterServiceJsonTest {

    public static final String EXPECTED_PRETTY_STRING = "{" +
            "  \"id\" : 1," +
            "  \"name\" : \"Fantasy\"" +
            "}";

    @Autowired
    private ObjectPrettyConverterService converterService;

    @Test
    @DisplayName(value = "get pretty string for object")
    void shouldGetPrettyString() {
        final Genre fantasy = Genre.builder()
                .id(1L)
                .name("Fantasy")
                .build();

        final String prettyString = converterService.getPrettyString(fantasy);

        assertThat(prettyString.replaceAll("\n", "")
                .replaceAll("\r", "")).isEqualTo(EXPECTED_PRETTY_STRING);
    }

}