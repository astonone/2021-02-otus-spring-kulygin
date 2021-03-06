package ru.otus.kulygin.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.service.LocaleService;

import java.util.Locale;

@Service
public class LocaleServiceImpl implements LocaleService {

    private final MessageSource messageSource;
    private final Locale locale;

    public LocaleServiceImpl(MessageSource messageSource, @Value("${app.locale}") Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public String getLocalizedString(String key) {
        return messageSource.getMessage(key, null, locale);
    }

}
