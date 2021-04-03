package ru.otus.kulygin.provider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = LocaleProvider.class)
@DisplayName(value = "LocaleProvider should ")
class LocaleProviderTest {

    @Configuration
    static class NestedConfiguration {
        @Bean
        MessageSource messageSource() {
            return mock(MessageSource.class);
        }

        @Bean
        LocaleProvider localeProvider() {
            return new LocaleProvider(messageSource(), Locale.ENGLISH);
        }
    }

    @Autowired
    private LocaleProvider localeProvider;

    @Autowired
    private MessageSource messageSource;

    @Test
    @DisplayName(value = "get message source")
    void getMessageSource() {
        final MessageSource result = localeProvider.getMessageSource();

        assertThat(result).isEqualTo(messageSource);
    }

    @Test
    @DisplayName(value = "get locale")
    void getLocale() {
        final Locale locale = localeProvider.getLocale();

        assertThat(locale).isEqualTo(Locale.ENGLISH);
    }

    @Test
    @DisplayName(value = "set locale")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void setLocale() {
        final Locale locale = localeProvider.getLocale();
        assertThat(locale).isEqualTo(Locale.ENGLISH);

        localeProvider.setLocale("fr");

        assertThat(localeProvider.getLocale()).isEqualTo(Locale.FRENCH);
    }
}