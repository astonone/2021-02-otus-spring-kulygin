package ru.otus.kulygin.facade.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.facade.UiFacade;
import ru.otus.kulygin.service.LocaleService;
import ru.otus.kulygin.service.UiService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UiLocalizedFacadeImpl.class)
@DisplayName(value = "UiLocalizedFacadeImpl should ")
class UiLocalizedFacadeImplTest {

    @Autowired
    private UiFacade uiFacade;

    @MockBean
    private UiService uiService;

    @MockBean
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
    @DisplayName(value = "get message from user")
    void shouldGetMessageFromUser() {
        when(uiService.in()).thenAnswer(a -> "lalalal");

        final String messageFromUser = uiFacade.getMessageFromUser();

        assertThat(messageFromUser).isNotNull().isEqualTo("lalalal");
    }

}