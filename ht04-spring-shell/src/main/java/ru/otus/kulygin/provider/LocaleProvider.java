package ru.otus.kulygin.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleProvider {

    private final MessageSource messageSource;
    private final Locale locale;

    public LocaleProvider(MessageSource messageSource, @Value("${app.locale}") Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public Locale getLocale() {
        return locale;
    }
}
