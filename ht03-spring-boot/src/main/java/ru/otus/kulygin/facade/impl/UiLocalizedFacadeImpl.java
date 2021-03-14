package ru.otus.kulygin.facade.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.facade.UiFacade;
import ru.otus.kulygin.service.LocaleService;
import ru.otus.kulygin.service.UiService;

@Service
public class UiLocalizedFacadeImpl implements UiFacade {

    private final UiService uiService;
    private final LocaleService localeService;

    public UiLocalizedFacadeImpl(UiService uiService, LocaleService localeService) {
        this.uiService = uiService;
        this.localeService = localeService;
    }

    @Override
    public void showMessageForUser(String message) {
        uiService.out(message);
    }

    @Override
    public void showLocalizedMessageForUser(String key, String... args) {
        uiService.out(localeService.getLocalizedString(key, args));
    }

    @Override
    public String getMessageFromUser() {
        return uiService.in();
    }

}
