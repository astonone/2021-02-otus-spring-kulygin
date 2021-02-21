package ru.otus.kulygin.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.dao.QuestionAndAnswerDAO;
import ru.otus.kulygin.domain.QuestionAndAnswer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName(value = "QuestionAndAnswerDAO should ")
class QuestionAndAnswerDAOImplTest {

    private final QuestionAndAnswerDAO questionAndAnswerDAO = new QuestionAndAnswerDAOImpl();

    @Test
    @DisplayName(value = "create question and answer")
    public void shouldCreateQuestionAndAnswer() {
        final QuestionAndAnswer questionAndAnswer = questionAndAnswerDAO.create("How much 2+2?", "4");

        assertThat(questionAndAnswer.getQuestion(), equalTo("How much 2+2?"));
        assertThat(questionAndAnswer.getAnswer(), equalTo("4"));
    }
}