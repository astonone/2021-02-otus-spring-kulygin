package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kulygin.service.LocaleService;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName(value = "LocaleServiceImpl should ")
class LocaleServiceImplTest {

    @Configuration
    static class NestedConfiguration {
        @Bean
        MessageSource messageSource() {
            return mock(MessageSource.class);
        }

        @Bean
        Locale locale() {
            return mock(Locale.class);
        }

        @Bean
        LocaleService localeService() {
            return new LocaleServiceImpl(messageSource(), locale());
        }
    }

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Locale locale;

    @Autowired
    private LocaleService localeService;

    @Test
    @DisplayName(value = "get localized string by key")
    void shouldGetLocalizedString() {
        when(messageSource.getMessage("my.super.key", null, locale)).thenReturn("You are wonderful!");

        final String localizedString = localeService.getLocalizedString("my.super.key");

        assertThat(localizedString).isEqualTo("You are wonderful!");
    }
}