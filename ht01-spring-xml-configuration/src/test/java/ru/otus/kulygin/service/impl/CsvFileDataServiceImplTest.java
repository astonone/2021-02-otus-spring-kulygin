package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.dao.QuestionAndAnswerDAO;
import ru.otus.kulygin.dao.impl.QuestionAndAnswerDAOImpl;
import ru.otus.kulygin.domain.QuestionAndAnswer;
import ru.otus.kulygin.service.DataService;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@DisplayName(value = "CsvFileDataService should ")
class CsvFileDataServiceImplTest {

    private final QuestionAndAnswerDAO questionAndAnswerDAO = new QuestionAndAnswerDAOImpl();
    private final DataService dataService = new CsvFileDataServiceImpl("data.csv", ";", questionAndAnswerDAO);

    @Test
    @DisplayName(value = "get list with questions and answers")
    void getQuestionsAndAnswers() throws IOException {
        final List<QuestionAndAnswer> questionsAndAnswers = dataService.getQuestionsAndAnswers();

        assertThat(questionsAndAnswers, hasSize(2));
        assertThat(questionsAndAnswers.get(0).getQuestion(), equalTo("How many days make up a leap year?"));
        assertThat(questionsAndAnswers.get(0).getAnswer(), equalTo("366"));
        assertThat(questionsAndAnswers.get(1).getQuestion(), equalTo("How many musicians are in a quintet?"));
        assertThat(questionsAndAnswers.get(1).getAnswer(), equalTo("5"));
    }
}