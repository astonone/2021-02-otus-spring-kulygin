package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.provider.LocaleProvider;
import ru.otus.kulygin.service.LocaleService;

@Service
public class LocaleServiceImpl implements LocaleService {

    private final LocaleProvider localeProvider;

    public LocaleServiceImpl(LocaleProvider localeProvider) {
        this.localeProvider = localeProvider;
    }

    @Override
    public String getLocalizedString(String key, String ...args) {
        return localeProvider.getMessageSource().getMessage(key, args, localeProvider.getLocale());
    }

}
