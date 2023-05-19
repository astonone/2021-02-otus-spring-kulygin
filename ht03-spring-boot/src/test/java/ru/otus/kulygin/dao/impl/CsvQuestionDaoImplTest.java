package ru.otus.kulygin.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.kulygin.dao.QuestionDao;
import ru.otus.kulygin.domain.Question;
import ru.otus.kulygin.exception.QuestionsLoadingException;
import ru.otus.kulygin.provider.CsvResourceProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CsvQuestionDaoImpl.class)
@DisplayName(value = "CsvQuestionDao should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CsvQuestionDaoImplTest {

    @Autowired
    private QuestionDao questionDao;

    @MockBean
    private CsvResourceProvider csvResourceProvider;

    @Test
    @DisplayName(value = "get list with questions and answers for english locale")
    public void shouldFindQuestions_enLocale() {
        when(csvResourceProvider.getPath()).thenReturn("data/en.csv");
        when(csvResourceProvider.getDelimiter()).thenReturn(";");

        final List<Question> questions = questionDao.findAll();

        assertThat(questions).hasSize(2);
        assertThat(questions.get(0).getQuestion()).isEqualTo("How many days make up a leap year?");
        assertThat(questions.get(0).getAnswer()).isEqualTo("366");
        assertThat(questions.get(1).getQuestion()).isEqualTo("How many musicians are in a quintet?");
        assertThat(questions.get(1).getAnswer()).isEqualTo("5");
    }

    @Test
    @DisplayName(value = "get list with questions and answers for russian locale")
    public void shouldFindQuestions_ruLocale() {
        when(csvResourceProvider.getPath()).thenReturn("data/ru.csv");
        when(csvResourceProvider.getDelimiter()).thenReturn(";");

        final List<Question> questions = questionDao.findAll();

        assertThat(questions).hasSize(2);
        assertThat(questions.get(0).getQuestion()).isEqualTo("Сколько суток составляют високосный год?");
        assertThat(questions.get(0).getAnswer()).isEqualTo("366");
        assertThat(questions.get(1).getQuestion()).isEqualTo("Сколько музыкантов в квинтете?");
        assertThat(questions.get(1).getAnswer()).isEqualTo("5");
    }

    @Test
    @DisplayName(value = "get list with questions and answers for estonian locale")
    public void shouldFindQuestions_etLocale() {
        when(csvResourceProvider.getPath()).thenReturn("data/et.csv");
        when(csvResourceProvider.getDelimiter()).thenReturn(";");

        final List<Question> questions = questionDao.findAll();

        assertThat(questions).hasSize(2);
        assertThat(questions.get(0).getQuestion()).isEqualTo("Mitu päeva moodustab liigaasta?");
        assertThat(questions.get(0).getAnswer()).isEqualTo("366");
        assertThat(questions.get(1).getQuestion()).isEqualTo("Mitu muusikut on kvintetis?");
        assertThat(questions.get(1).getAnswer()).isEqualTo("5");
    }

    @Test
    @DisplayName(value = "throw exception when file has not exists")
    public void shouldThrowExceptionWhenFileHasNotExists() {
        when(csvResourceProvider.getPath()).thenReturn("lala/en.csv");
        when(csvResourceProvider.getDelimiter()).thenReturn(";");

        Throwable throwable = assertThrows(QuestionsLoadingException.class, () -> questionDao.findAll());

        assertThat(throwable.getMessage()).isEqualTo("Csv data file has not found");
    }

    @Test
    @DisplayName(value = "throw exception when delimiter is incorrect")
    public void shouldThrowExceptionWhenDelimiterIsIncorrect() {
        when(csvResourceProvider.getPath()).thenReturn("data/en.csv");
        when(csvResourceProvider.getDelimiter()).thenReturn("!");

        Throwable throwable = assertThrows(QuestionsLoadingException.class, () -> questionDao.findAll());

        assertThat(throwable.getMessage()).isEqualTo("Incorrect data structure in csv file");
    }

}