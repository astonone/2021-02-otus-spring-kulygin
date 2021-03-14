package ru.otus.kulygin.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CsvResourceProvider {

    private final String path;
    private final String delimiter;

    public CsvResourceProvider(@Value("${test.file.path}") String path, @Value("${test.file.delimiter}") String delimiter) {
        this.path = path;
        this.delimiter = delimiter;
    }

    public String getPath() {
        return path;
    }

    public String getDelimiter() {
        return delimiter;
    }

}
