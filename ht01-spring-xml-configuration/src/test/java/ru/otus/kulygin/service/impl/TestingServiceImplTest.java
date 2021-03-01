package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.domain.Question;
import ru.otus.kulygin.domain.Student;
import ru.otus.kulygin.domain.TestResult;
import ru.otus.kulygin.exception.QuestionsLoadingException;
import ru.otus.kulygin.exception.UserInputException;
import ru.otus.kulygin.service.QuestionService;
import ru.otus.kulygin.service.TestingService;
import ru.otus.kulygin.service.UiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName(value = "TestingServiceImpl should ")
class TestingServiceImplTest {

    private TestingService testingService;
    private UiService uiService;
    private QuestionService questionService;

    @BeforeEach
    public void init() {
        uiService = mock(UiService.class);
        questionService = mock(QuestionService.class);
        testingService = new TestingServiceImpl(questionService, uiService);
    }

    @Test
    @DisplayName(value = "start testing process and successfully finish it")
    void shouldDoTest() {
        final Student student = new Student("Ivan", "Ivanov");
        List<Question> questionsAndAnswers = new ArrayList<>();
        questionsAndAnswers.add(new Question("How much 2+2?", "4"));
        questionsAndAnswers.add(new Question("How much 2*2?", "4"));
        questionsAndAnswers.add(new Question("How much 2/2?", "1"));
        questionsAndAnswers.add(new Question("How much 2+3?", "5"));
        questionsAndAnswers.add(new Question("How much 2+5?", "7"));
        when(questionService.findAll()).thenReturn(questionsAndAnswers);
        when(uiService.in())
                .thenAnswer(a -> "4")
                .thenAnswer(a -> "4")
                .thenAnswer(a -> "2")
                .thenAnswer(a -> "5")
                .thenAnswer(a -> "7");

        final TestResult testResult = testingService.doTest(student);

        assertThat(testResult).isNotNull();
        assertThat(testResult.getStudent()).isEqualTo(student);
        assertThat(testResult.getMark()).isEqualTo(4);
    }

    @Test
    @DisplayName(value = "start testing process with input error")
    void shouldNotDoTest_inputError() {
        final Student student = new Student("Ivan", "Ivanov");
        List<Question> questionsAndAnswers = new ArrayList<>();
        questionsAndAnswers.add(new Question("How much 2+2?", "4"));
        questionsAndAnswers.add(new Question("How much 2*2?", "4"));
        questionsAndAnswers.add(new Question("How much 2/2?", "1"));
        questionsAndAnswers.add(new Question("How much 2+3?", "5"));
        questionsAndAnswers.add(new Question("How much 2+5?", "7"));
        when(uiService.in()).thenThrow(new UserInputException(new IOException("Aaaaaa, console was damaged!")));
        when(questionService.findAll()).thenReturn(questionsAndAnswers);

        Throwable throwable = assertThrows(UserInputException.class, () -> testingService.doTest(student));

        assertThat(throwable.getMessage()).isEqualTo("java.io.IOException: Aaaaaa, console was damaged!");
    }

    @Test
    @DisplayName(value = "start testing process with loading questions error")
    void shouldNotDoTest_loadingQuestionsError() {
        final Student student = new Student("Ivan", "Ivanov");
        when(questionService.findAll()).thenThrow(new QuestionsLoadingException("Data file was stolen by thief", new IOException()));

        Throwable throwable = assertThrows(QuestionsLoadingException.class, () -> testingService.doTest(student));

        assertThat(throwable.getMessage()).isEqualTo("Data file was stolen by thief");
    }
}