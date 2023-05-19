package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.service.LocaleService;
import ru.otus.kulygin.service.ObjectPrettyConverterService;
import ru.otus.kulygin.service.UiService;

@Service
public class UiServiceLocalized implements UiService {

    private final LocaleService localeService;
    private final ObjectPrettyConverterService objectPrettyConverterService;

    public UiServiceLocalized(LocaleService localeService, ObjectPrettyConverterService objectPrettyConverterService) {
        this.localeService = localeService;
        this.objectPrettyConverterService = objectPrettyConverterService;
    }

    @Override
    public String getLocalizedMessageForUser(String key, String... args) {
        return localeService.getLocalizedString(key, args);
    }

    @Override
    public String getObjectForPrettyPrint(Object object) {
        return objectPrettyConverterService.getPrettyString(object);
    }

}
