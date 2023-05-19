package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import ru.otus.kulygin.service.LocaleService;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName(value = "LocaleServiceImpl should ")
class LocaleServiceImplTest {

    private static MessageSource messageSource;
    private static Locale locale;
    private static LocaleService localeService;

    @BeforeAll
    public static void init() {
        messageSource = mock(MessageSource.class);
        locale = mock(Locale.class);
        localeService = new LocaleServiceImpl(messageSource, locale);
    }

    @Test
    @DisplayName(value = "get localized string by key")
    void shouldGetLocalizedString() {
        when(messageSource.getMessage("my.super.key", null, locale)).thenReturn("You are wonderful!");

        final String localizedString = localeService.getLocalizedString("my.super.key");

        assertThat(localizedString).isEqualTo("You are wonderful!");
    }
}