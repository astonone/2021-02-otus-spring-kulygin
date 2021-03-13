package ru.otus.kulygin.facade.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.kulygin.facade.UiFacade;
import ru.otus.kulygin.service.LocaleService;
import ru.otus.kulygin.service.UiService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName(value = "ConsoleUiLocalizedFacadeImpl should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ConsoleUiLocalizedFacadeImplTest {

    @Configuration
    static class NestedConfiguration {
        @Bean
        LocaleService localeService() {
            return mock(LocaleService.class);
        }

        @Bean
        UiService uiService() {
            return mock(UiService.class);
        }

        @Bean
        UiFacade uiFacade() {
            return new ConsoleUiLocalizedFacadeImpl(uiService(), localeService());
        }
    }

    @Autowired
    private UiFacade uiFacade;

    @Autowired
    private UiService uiService;

    @Autowired
    private LocaleService localeService;

    @Test
    @DisplayName(value = "show message for user without localization")
    void shouldShowMessageForUser() {
        uiFacade.showMessageForUser("test");

        verify(uiService).out("test");
    }

    @Test
    @DisplayName(value = "show localized message for user")
    void shouldShowLocalizedMessageForUser() {
        when(localeService.getLocalizedString("test.key")).thenReturn("hello");

        uiFacade.showLocalizedMessageForUser("test.key");

        verify(localeService).getLocalizedString("test.key");
        verify(uiService).out("hello");
    }

    @Test
    @DisplayName(value = "get localized message for user")
    void shouldGetLocalizedMessageForUser() {
        when(localeService.getLocalizedString("test.key")).thenReturn("hello");

        String message = uiFacade.getLocalizedMessageForUser("test.key");

        verify(localeService).getLocalizedString("test.key");
        assertThat(message).isEqualTo("hello");
    }

    @Test
    @DisplayName(value = "get message from user")
    void shouldGetMessageFromUser() {
        when(uiService.in()).thenAnswer(a -> "lalalal");

        final String messageFromUser = uiFacade.getMessageFromUser();

        assertThat(messageFromUser).isNotNull().isEqualTo("lalalal");
    }

}