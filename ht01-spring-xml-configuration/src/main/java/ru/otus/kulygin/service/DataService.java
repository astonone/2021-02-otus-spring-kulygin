package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.QuestionAndAnswer;

import java.io.IOException;
import java.util.List;

public interface DataService {
    List<QuestionAndAnswer> getQuestionsAndAnswers() throws IOException;
}
