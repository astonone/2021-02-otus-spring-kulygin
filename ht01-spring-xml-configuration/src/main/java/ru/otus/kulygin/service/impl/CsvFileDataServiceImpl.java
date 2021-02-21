package ru.otus.kulygin.service.impl;

import org.springframework.core.io.ClassPathResource;
import ru.otus.kulygin.dao.QuestionAndAnswerDAO;
import ru.otus.kulygin.domain.QuestionAndAnswer;
import ru.otus.kulygin.service.DataService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFileDataServiceImpl implements DataService {

    private final String filename;
    private final String delimiter;
    private final QuestionAndAnswerDAO questionAndAnswerDAO;

    public CsvFileDataServiceImpl(String filename, String delimiter, QuestionAndAnswerDAO questionAndAnswerDAO) {
        this.filename = filename;
        this.delimiter = delimiter;
        this.questionAndAnswerDAO = questionAndAnswerDAO;
    }

    public String getFilename() {
        return filename;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public List<QuestionAndAnswer> getQuestionsAndAnswers() throws IOException {
        final InputStream inputStream = loadQuestions();

        if (inputStream == null) {
            throw new IllegalArgumentException("Problems with loading questions data");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> questionAndAnswerString = new ArrayList<>();
        String str;

        while ((str = reader.readLine()) != null) {
            questionAndAnswerString.add(str);
        }

        return questionAndAnswerString.stream().map(line -> {
            String[] questionAndAnswer = line.split(getDelimiter());
            return questionAndAnswerDAO.create(questionAndAnswer[0], questionAndAnswer[1]);
        }).collect(Collectors.toList());
    }

    private InputStream loadQuestions() {
        try {
            return new ClassPathResource(getFilename()).getInputStream();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
