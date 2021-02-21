package ru.otus.kulygin.dao.impl;

import ru.otus.kulygin.dao.QuestionAndAnswerDAO;
import ru.otus.kulygin.domain.QuestionAndAnswer;

public class QuestionAndAnswerDAOImpl implements QuestionAndAnswerDAO {

    @Override
    public QuestionAndAnswer create(String question, String answer) {
        return new QuestionAndAnswer(question, answer);
    }
}
