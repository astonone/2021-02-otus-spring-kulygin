package ru.otus.kulygin.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.dao.QuestionDao;
import ru.otus.kulygin.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(value = "CsvQuestionDao should ")
class CsvQuestionDaoImplTest {

    private final QuestionDao questionDao = new CsvQuestionDaoImpl("data.csv", ";");

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
}