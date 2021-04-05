package ru.otus.kulygin.service;

public interface UiService {

    String getLocalizedMessageForUser(String key, String ...args);
    String getObjectForPrettyPrint(Object object);

}
