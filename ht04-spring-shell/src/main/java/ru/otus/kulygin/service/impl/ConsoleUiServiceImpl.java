package ru.otus.kulygin.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.exception.UserInputException;
import ru.otus.kulygin.provider.UserIOProvider;
import ru.otus.kulygin.service.UiService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ConsoleUiServiceImpl implements UiService {

    private final UserIOProvider userIOProvider;

    public ConsoleUiServiceImpl(UserIOProvider userIOProvider) {
        this.userIOProvider = userIOProvider;
    }

    @Override
    public String in() {
        try {
            return new BufferedReader(new InputStreamReader(userIOProvider.getInput())).readLine();
        } catch (IOException e) {
            throw new UserInputException(e);
        }
    }

    @Override
    public void out(String stringForPrint) {
        userIOProvider.getOutput().println(stringForPrint);
    }

}
