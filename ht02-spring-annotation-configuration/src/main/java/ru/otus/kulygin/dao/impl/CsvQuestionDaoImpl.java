package ru.otus.kulygin.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import ru.otus.kulygin.dao.QuestionDao;
import ru.otus.kulygin.domain.Question;
import ru.otus.kulygin.exception.QuestionsLoadingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CsvQuestionDaoImpl implements QuestionDao {

    private final String path;
    private final String delimiter;

    public CsvQuestionDaoImpl(@Value("${test.file.path}") String path, @Value("${test.file.delimiter}") String delimiter) {
        this.path = path;
        this.delimiter = delimiter;
    }

    @Override
    public List<Question> findAll() {
        return loadQuestionsFromCsvFile();
    }

    private List<Question> loadQuestionsFromCsvFile() {
        final InputStream inputStream = getQuestionsInputStream();

        List<String> questionAndAnswerString = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String str;
            while ((str = reader.readLine()) != null) {
                questionAndAnswerString.add(str);
            }
        } catch (IOException e) {
            throw new QuestionsLoadingException("Problems with loading questions data", e);
        }

        return questionAndAnswerString.stream().map(line -> {
            try {
                String[] questionAndAnswer = line.split(this.delimiter);
                return new Question(questionAndAnswer[0], questionAndAnswer[1]);
            } catch (Exception e) {
                throw new QuestionsLoadingException("Incorrect data structure in csv file", e);
            }
        }).collect(Collectors.toList());
    }

    private InputStream getQuestionsInputStream() {
        try {
            return new ClassPathResource(path).getInputStream();
        } catch (IOException e) {
            throw new QuestionsLoadingException("Csv data file has not found", e);
        }
    }
}
