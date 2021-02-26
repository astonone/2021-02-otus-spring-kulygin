package ru.otus.kulygin.service.impl;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.service.UiService;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName(value = "UiServiceImpl should ")
class UiServiceImplTest {

    private static UiService uiService;
    private static PrintStream out;

    @BeforeAll
    public static void init() {
        out = mock(PrintStream.class);
        uiService = new UiServiceImpl(IOUtils.toInputStream("Hello", StandardCharsets.UTF_8), out);
    }

    @Test
    @DisplayName(value = "get user in")
    void shouldIn() {
        final String result = uiService.in();

        assertThat(result).isNotEmpty().isEqualTo("Hello");
    }

    @Test
    @DisplayName(value = "create out for user")
    void shouldOut() {
        uiService.out("Test");

        verify(out).println("Test");
    }
}