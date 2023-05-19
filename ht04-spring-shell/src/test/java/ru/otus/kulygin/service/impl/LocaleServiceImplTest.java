package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.kulygin.provider.LocaleProvider;
import ru.otus.kulygin.service.LocaleService;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = LocaleServiceImpl.class)
@DisplayName(value = "LocaleServiceImpl should ")
class LocaleServiceImplTest {

    @Autowired
    private LocaleService localeService;

    @MockBean
    private LocaleProvider localeProvider;

    @Test
    @DisplayName(value = "get localized string by key")
    void shouldGetLocalizedString() {
        when(localeProvider.getMessageSource()).thenReturn(mock(MessageSource.class));
        when(localeProvider.getLocale()).thenReturn(mock(Locale.class));

        when(localeProvider.getMessageSource().getMessage("my.super.key",
                new String[]{}, localeProvider.getLocale())).thenReturn("You are wonderful!");

        final String localizedString = localeService.getLocalizedString("my.super.key");
        assertThat(localizedString).isEqualTo("You are wonderful!");
    }
}