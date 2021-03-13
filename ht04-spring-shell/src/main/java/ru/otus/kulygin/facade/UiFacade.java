package ru.otus.kulygin.facade;

public interface UiFacade {

    void showMessageForUser(String message);
    void showLocalizedMessageForUser(String key, String ...args);
    String getLocalizedMessageForUser(String key, String ...args);
    String getMessageFromUser();

}
