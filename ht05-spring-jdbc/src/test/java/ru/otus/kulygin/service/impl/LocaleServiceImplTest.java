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

    public static final String LANGUAGE_KEY = "my.super.key";
    public static final String LANGUAGE_VALUE = "You are wonderful!";
    public static final String LANGUAGE_KEY_WITH_ARGS = "my.super.key2";
    public static final String LANGUAGE_VALUE_WITH_ARGS = "Sanya, You are wonderful!";
    public static final String ARG = "Sanya";

    @Autowired
    private LocaleService localeService;

    @MockBean
    private LocaleProvider localeProvider;

    @Test
    @DisplayName(value = "get localized string by key")
    void shouldGetLocalizedString() {
        when(localeProvider.getMessageSource()).thenReturn(mock(MessageSource.class));
        when(localeProvider.getLocale()).thenReturn(mock(Locale.class));

        when(localeProvider.getMessageSource().getMessage(LANGUAGE_KEY,
                new String[]{}, localeProvider.getLocale())).thenReturn(LANGUAGE_VALUE);

        final String localizedString = localeService.getLocalizedString(LANGUAGE_KEY);
        assertThat(localizedString).isEqualTo(LANGUAGE_VALUE);
    }

    @Test
    @DisplayName(value = "get localized string by key with arguments")
    void shouldGetLocalizedStringWithArguments() {
        when(localeProvider.getMessageSource()).thenReturn(mock(MessageSource.class));
        when(localeProvider.getLocale()).thenReturn(mock(Locale.class));

        when(localeProvider.getMessageSource().getMessage(LANGUAGE_KEY_WITH_ARGS,
                new String[]{ARG}, localeProvider.getLocale())).thenReturn(LANGUAGE_VALUE_WITH_ARGS);

        final String localizedString = localeService.getLocalizedString(LANGUAGE_KEY_WITH_ARGS, ARG);
        assertThat(localizedString).isEqualTo(LANGUAGE_VALUE_WITH_ARGS);
    }

}