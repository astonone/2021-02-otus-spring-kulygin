package ru.otus.kulygin.service.impl;

import ru.otus.kulygin.exception.UserInputException;
import ru.otus.kulygin.service.UiService;

import java.io.*;

public class UiServiceImpl implements UiService {

    private final InputStream input;
    private final PrintStream output;

    public UiServiceImpl(InputStream input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public String in() {
        try {
            return new BufferedReader(new InputStreamReader(input)).readLine();
        } catch (IOException e) {
            throw new UserInputException(e);
        }
    }

    @Override
    public void out(String stringForPrint) {
        output.println(stringForPrint);
    }

}
