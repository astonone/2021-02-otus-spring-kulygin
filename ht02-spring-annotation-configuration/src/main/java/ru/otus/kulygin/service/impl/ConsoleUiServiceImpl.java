package ru.otus.kulygin.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.exception.UserInputException;
import ru.otus.kulygin.service.UiService;

import java.io.*;

@Service
public class ConsoleUiServiceImpl implements UiService {

    private final InputStream input;
    private final PrintStream output;

    public ConsoleUiServiceImpl(@Value("#{ T(java.lang.System).in }") InputStream input,
                                @Value("#{ T(java.lang.System).out }") PrintStream output) {
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
