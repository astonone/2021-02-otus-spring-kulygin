package ru.otus.kulygin.dao;

import ru.otus.kulygin.domain.QuestionAndAnswer;

public interface QuestionAndAnswerDAO {
    QuestionAndAnswer create(String question, String answer);
}
