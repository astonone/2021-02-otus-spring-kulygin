package ru.otus.kulygin.service.impl;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.kulygin.exception.UserInputException;
import ru.otus.kulygin.provider.UserIOProvider;
import ru.otus.kulygin.service.UiService;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ConsoleUiServiceImpl.class)
@DisplayName(value = "UiServiceImpl should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ConsoleUiServiceImplTest {

    @Autowired
    private UiService uiService;

    @MockBean
    private UserIOProvider userIOProvider;

    @Test
    @DisplayName(value = "get user in")
    void shouldIn() {
        when(userIOProvider.getInput()).thenReturn(IOUtils.toInputStream("Hello", StandardCharsets.UTF_8));

        final String result = uiService.in();

        assertThat(result).isNotEmpty().isEqualTo("Hello");
    }

    @Test
    @DisplayName(value = "not get user in")
    void shouldNotIn() {
        when(userIOProvider.getInput()).thenReturn(mock(InputStream.class));

        Throwable throwable = assertThrows(UserInputException.class, () -> uiService.in());

        assertThat(throwable.getMessage()).isNotEmpty().isEqualTo("java.io.IOException: Underlying input stream returned zero bytes");
    }

    @Test
    @DisplayName(value = "create out for user")
    void shouldOut() {
        when(userIOProvider.getOutput()).thenReturn(mock(PrintStream.class));

        uiService.out("Test");

        verify(userIOProvider.getOutput()).println("Test");
    }
}