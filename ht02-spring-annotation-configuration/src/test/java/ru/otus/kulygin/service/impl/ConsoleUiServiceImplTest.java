package ru.otus.kulygin.service.impl;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.exception.UserInputException;
import ru.otus.kulygin.service.UiService;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName(value = "UiServiceImpl should ")
class ConsoleUiServiceImplTest {

    private UiService uiService;
    private PrintStream out;

    @BeforeEach
    public void init() {
        out = mock(PrintStream.class);
        uiService = new ConsoleUiServiceImpl(mock(InputStream.class), out);
    }

    @Test
    @DisplayName(value = "not get user in")
    void shouldIn() {
        uiService = new ConsoleUiServiceImpl(IOUtils.toInputStream("Hello", StandardCharsets.UTF_8), out);

        final String result = uiService.in();

        assertThat(result).isNotEmpty().isEqualTo("Hello");
    }

    @Test
    @DisplayName(value = "get user in")
    void shouldNotIn() {
        Throwable throwable = assertThrows(UserInputException.class, () -> uiService.in());

        assertThat(throwable.getMessage()).isNotEmpty().isEqualTo("java.io.IOException: Underlying input stream returned zero bytes");
    }

    @Test
    @DisplayName(value = "create out for user")
    void shouldOut() {
        uiService.out("Test");

        verify(out).println("Test");
    }
}