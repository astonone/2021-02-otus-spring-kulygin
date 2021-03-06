package ru.otus.kulygin.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.dao.QuestionDao;
import ru.otus.kulygin.domain.Question;
import ru.otus.kulygin.exception.QuestionsLoadingException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName(value = "CsvQuestionDao should ")
class CsvQuestionDaoImplTest {

    private QuestionDao questionDao = new CsvQuestionDaoImpl("data.csv", ";");

    @Test
    @DisplayName(value = "get list with questions and answers")
    public void shouldFindQuestions() {
        final List<Question> questions = questionDao.findAll();

        assertThat(questions).hasSize(2);
        assertThat(questions.get(0).getQuestion()).isEqualTo("How many days make up a leap year?");
        assertThat(questions.get(0).getAnswer()).isEqualTo("366");
        assertThat(questions.get(1).getQuestion()).isEqualTo("How many musicians are in a quintet?");
        assertThat(questions.get(1).getAnswer()).isEqualTo("5");
    }

    @Test
    @DisplayName(value = "throw exception when file has not exists")
    public void shouldThrowExceptionWhenFileHasNotExists() {
        questionDao = new CsvQuestionDaoImpl("lala.csv", ";");
        Throwable throwable = assertThrows(QuestionsLoadingException.class, () -> questionDao.findAll());

        assertThat(throwable.getMessage()).isEqualTo("Csv data file has not found");
    }

    @Test
    @DisplayName(value = "throw exception when delimiter is incorrect")
    public void shouldThrowExceptionWhenDelimiterIsIncorrect() {
        questionDao = new CsvQuestionDaoImpl("data.csv", "!");
        Throwable throwable = assertThrows(QuestionsLoadingException.class, () -> questionDao.findAll());

        assertThat(throwable.getMessage()).isEqualTo("Incorrect data structure in csv file");
    }
}