package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.service.ObjectPrettyConverterService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ObjectPrettyConverterServiceJson.class)
@DisplayName(value = "ObjectPrettyConverterServiceJson should ")
class ObjectPrettyConverterServiceJsonTest {

    public static final String EXPECTED_PRETTY_STRING = "{" +
            "  \"code\" : 1," +
            "  \"message\" : \"test\"" +
            "}";

    @Autowired
    private ObjectPrettyConverterService converterService;

    @Test
    @DisplayName(value = "get pretty string for object")
    void shouldGetPrettyString() {
        final ErrorDto errorDto = ErrorDto.builder()
                .code(1L)
                .message("test")
                .build();

        final String prettyString = converterService.getPrettyString(errorDto);

        assertThat(prettyString.replaceAll("\n", "")
                .replaceAll("\r", "")).isEqualTo(EXPECTED_PRETTY_STRING);
    }

}