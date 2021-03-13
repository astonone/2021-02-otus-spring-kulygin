package ru.otus.kulygin.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;

@Component
public class UserIOProvider {

    private final InputStream input;
    private final PrintStream output;

    public UserIOProvider(@Value("#{ T(java.lang.System).in }") InputStream input,
                          @Value("#{ T(java.lang.System).out }") PrintStream output) {
        this.input = input;
        this.output = output;
    }

    public InputStream getInput() {
        return input;
    }

    public PrintStream getOutput() {
        return output;
    }

}
